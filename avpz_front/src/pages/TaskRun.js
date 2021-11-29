import React from "react";
import { DndProvider, useDrag, useDrop } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import { withRouter } from "react-router";
import ReactTooltip from 'react-tooltip';
import '../css/TaskRun.css';

export const ItemTypes = {
    REQUIREMENT: 'requirement'
}

function RequirementItem(props) {
    let data_for = props.id.toString()

    const [{isDragging}, drag] = useDrag(() => ({
        type: ItemTypes.REQUIREMENT,
        item: {id: props.id, name: props.name, onDropAction: props.onDropAction},

        end(props, monitor) {
            const item = monitor.getItem();
            const dropRes = monitor.getDropResult();
            if (dropRes) {
                props.onDropAction(item.id, dropRes.id)
            }
        },

        collect: monitor => ({
            isDragging: monitor.isDragging(),
        }),
    }));

    const reqItemStyle = {
        opacity: isDragging ? 0 : 1,
        border: props.answers.check === false ? "2px solid grey" : props.answers.curr_answer === props.answers.real_answer ? "3px solid green" : "3px solid red"
    };

    return (
        <div data-tip data-for={data_for} ref={drag} style={reqItemStyle} className={props.answers.curr_answer !== 0 ? "taskRun-variant" : "taskRun-variant-special"}>
            {props.name}
            {props.children}
        </div>
    )
}

function Column(props) {
    let id = props.id;
    const [{ isOver }, drop] = useDrop(() => ({
        accept: ItemTypes.REQUIREMENT,

        drop(props, monitor) {
            return {id: id}
        },

        collect: (monitor) => ({
            isOver: monitor.isOver()
        }),
    }));

    const colItemStyle = {
        opacity: isOver ? '50%' : "100%"
    };

    return (
        <div ref={drop} style={colItemStyle} className={id !== 0 ? "taskRun-column" : "taskRun-column-special"}>
			{props.name}
            {props.children}
		</div>
    );
}

class TaskRun extends React.Component 
{
    constructor(props)
    {
        super(props)
        let realLevelSeach = new URLSearchParams(this.props.location.search)
        this.state = {
            variants: [],
            categories: [],
            check: false,
            level: realLevelSeach.get("level"),
            time: "1h 20m",
            error: ""
        }
        this.onCheckClick = this.onCheckClick.bind(this)
    }

    /* TODO */
    /* ERRORS */
    componentDidMount = async () => {
        
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/tasks/' + this.props.match.params.taskId + '?level=' + this.state.level);
        const data_tasks = await response_tasks.json();

        let curr_variants = data_tasks.variantDTOList;
        curr_variants.forEach(element => {
            element.curr_answer = 0;
        });

        this.setState({categories:data_tasks.categoryDtos, variants: curr_variants})
    }

    onCheckClick = () => {
        let checkState = this.state.check === true ? false : true;
        this.setState({check:checkState});
    }

    update = (id, status) => {
        let variants = [...this.state.variants];
        let index = variants.findIndex(x => x.uid === id);
        variants[index].curr_answer = status;

        this.setState({variants:variants});
    };

    onDoneClick = async () => {
        let data = {answers: this.state.variants, time: this.state.time, level:this.state.level}

        //Check if user completes task
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/evaluation/' + this.props.match.params.taskId, {method:"POST", body: JSON.stringify(data), credentials:'include', headers: { 'Content-Type': 'application/json', }});
        const data_tasks = await response_tasks.json();
        if (data_tasks.errorMessage !== 0)
        {
            this.props.history.push({
                pathname:'/modules/' + this.props.match.params.module  + '/tasks/complete/' + this.props.match.params.taskId, 
                state: {score:data_tasks.score, time: this.state.time}
            })

            return
        }

        this.setState({error: data_tasks.errorMessage})
    }

    onTimeSave = async (totalTime) => {
        console.log('Total time spent by user:', totalTime);
        //where to save data???
    }

    render() {
        return (
            <div className="taskRun-area-main">
                <DndProvider backend={HTML5Backend}>
                    <div className="taskRun-area-answers">
                        {
                            this.state.categories.map((column) => (
                                <Column key={column.uid} id={column.uid} name={column.displayName}>
                                    {
                                        this.state.variants.filter((item) => item.curr_answer === column.uid).map((answer) => (
                                            <React.Fragment>
                                                <RequirementItem key={answer.uid} id={answer.uid} name={answer.displayName} answers={{curr_answer:answer.curr_answer, real_answer:answer.categoryUid, check:this.state.check}} onDropAction={this.update}/>
                                                {
                                                    this.state.level === "HARD" ? null : (
                                                        <ReactTooltip key={answer.uid*2} id={answer.uid.toString()} place="right" type="info" effect="float">
                                                            <span>{answer.tooltip}</span>
                                                        </ReactTooltip>
                                                    )
                                                }
                                            </React.Fragment>
                                        ))
                                    }
                                </Column>
                            ))
                        }
                    </div>
                    <div className="taskRun-area-questions">
                        <Column id={0} name={""}>
                            <div className="taskRun-column-special-wrapper">
                                {
                                    this.state.variants.filter((item) => item.curr_answer === 0).map((answer) => (
                                        <React.Fragment>
                                            <RequirementItem key={answer.uid} id={answer.uid} name={answer.displayName} answers={{curr_answer:answer.curr_answer, real_answer:answer.categoryUid, check:this.state.check}} onDropAction={this.update}/>
                                            {
                                                (this.state.level === "HARD" || this.state.level === "NORMAL") ? null : (
                                                            <ReactTooltip key={answer.uid*2} id={answer.uid.toString()} place="right" type="info" effect="float">
                                                                <span>{answer.tooltip}</span>
                                                            </ReactTooltip>
                                                        )
                                            }
                                        </React.Fragment>
                                    ))
                                }
                            </div>
                        </Column>
                        <div className="taskRun-area-questions-actions">
                            <button className="btn btn-primary" disabled={this.state.level === "HARD" ? true : false} onClick={this.onCheckClick}>Check</button>
                            <button className="btn btn-success" onClick={this.onDoneClick}>Finish</button>
                        </div>
                    </div>
                </DndProvider>
            </div>
        )
    }
}

export default withRouter(TaskRun); 