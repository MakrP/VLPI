import React from "react";
import { DndProvider, useDrag, useDrop } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import '../css/TaskEdit.css';
import { withRouter } from "react-router";
import ReactTooltip from 'react-tooltip';

export const ItemTypes = {
    REQUIREMENT: 'requirement',
    COLUMN:'column'
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
        <div data-tip data-for={data_for} ref={drag} style={reqItemStyle} className={props.answers.curr_answer !== 0 ? "taskEdit-variant" : "taskEdit-variant-special"}>
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
        <div ref={drop} style={colItemStyle} className={id !== 0 ? "taskEdit-column" : "taskEdit-column-special"}>
            <div className="taskEdit-column-action">
                {props.name}
                <div className="taskEdit-column-action-del">
                    {id === 0 ? null : <i onClick={() => {props.onDeleteColumn(id)}} class="fas fa-minus-circle"></i>}
                </div>
            </div>
            {props.children}
		</div>
    );
}

function Bin(props) {
    const [{ isOver }, drop] = useDrop(() => ({
        accept: ItemTypes.REQUIREMENT,

        drop(props, monitor) {
            return {id: -1}
        },

        collect: (monitor) => ({
            isOver: monitor.isOver()
        }),
    }));

    const colItemStyle = {
        opacity: isOver ? '50%' : "100%",
        color: isOver ? "red" : "black"
    };

    return (
        <div ref={drop} style={colItemStyle} className={"taskEdit-remove-bin"}>
            <i class="fas fa-dumpster-fire"></i>
		</div>
    );
}

class TaskAdd extends React.Component 
{
    constructor(props)
    {
        super(props)
        this.state = {
            variants: [],
            categories: [],
            name: "",
            module: "",
            topic:"",
            check: false,
            error: "",
            uid: 0
        }
    }

    componentDidMount = async () => {
        
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/tasks/' + this.props.match.params.taskId + '?level=EASY');
        const data_tasks = await response_tasks.json();

        let curr_variants = data_tasks.variantDTOList;
        curr_variants.forEach(element => {
            element.curr_answer = element.categoryUid;
        });

        this.setState({categories:data_tasks.categoryDtos, variants: curr_variants})
    }

    onCategoryAddClick = () => {
        let cat_name = document.getElementById('category').value
        let curr_category = [...this.state.categories]
        let curr_uid = this.state.uid + 1

        curr_category.push({uid:curr_uid, displayName: cat_name})
        this.setState({categories: curr_category, uid: curr_uid})
    }

    onVariantAddClick = () => {
        let val_name = document.getElementById('variant-name').value
        let val_tooltip = document.getElementById('variant-tooltip').value
        let curr_variants = [...this.state.variants]
        let curr_uid = this.state.uid + 1

        curr_variants.push({uid: curr_uid, displayName: val_name, real_answer: 0, curr_answer:0, tooltip: val_tooltip})
        this.setState({variants: curr_variants, uid: curr_uid})
    }

    onDeleteColumn = (id) => {
        let curr_categories = [...this.state.categories];
        let curr_variants = [...this.state.variants];
        let index = this.state.categories.findIndex(x => x.uid === id);
        let curr_uid = this.state.uid + 1
        
        curr_variants.forEach((elem) => {
            if(elem.curr_answer === curr_categories[index].uid)
            {
                elem.curr_answer = 0
            }
        });

        curr_categories.splice(index,1)
        this.setState({categories: curr_categories, uid: curr_uid, variants:curr_variants})
    }

    update = (id, status) => {
        let variants = [...this.state.variants];
        let index = variants.findIndex(x => x.uid === id);

        if (status !== -1)
        {
            variants[index].curr_answer = status;    
        }
        else
        {
            variants.splice(index, 1)
        }

        this.setState({variants:variants});
    };

    onDoneClick = async () => {
        //TODO
    }

    render() {
        return (
            <div className="taskEdit-area-main">
                <DndProvider backend={HTML5Backend}>
                    <div className="taskEdit-area-answers">
                        <div className="taskEdit-actions">
                            <div className="taskEdit-category">
                                <input id='category' placeholder="Category" className='form-control'/>
                                <button className="btn btn-primary" onClick={this.onCategoryAddClick}>Add</button>
                            </div>
                            <div className="taskEdit-variant-add">
                                <input id='variant-name' placeholder="Requirement" className='form-control'/>
                                <input id='variant-tooltip' placeholder="Help" className='form-control'/>
                                <button className="btn btn-primary" onClick={this.onVariantAddClick}>Add</button>
                            </div>
                            <div className="taskEdit-task-other">
                                <input onChange={(event) => {this.setState({name:event.target.value})}} placeholder="Name" className='form-control'/>
                                <select onChange={(event) => {this.setState({module:event.target.value})}}>
                                    <option>Requirement Analysis</option>
                                    <option>Modelling</option>
                                    <option>Coding</option>
                                    <option>Testing</option>
                                    <option>Accompaniment</option>
                                </select>
                            </div>
                            <div className="taskEdit-area-questions-actions">
                                <button className="btn btn-success" onClick={this.onDoneClick}>Finish</button>
                            </div>
                        </div>
                        <div className="taskEdit-area-categories-add">
                            {
                                this.state.categories.map((column) => (
                                    <Column key={column.uid} id={column.uid} name={column.displayName} onDeleteColumn={this.onDeleteColumn}>
                                        {
                                            this.state.variants.filter((item) => item.curr_answer === column.uid).map((answer) => (
                                                <React.Fragment>
                                                    <RequirementItem key={answer.uid} id={answer.uid} name={answer.displayName} answers={{curr_answer:answer.curr_answer, real_answer:answer.curr_answer, check:this.state.check}} onDropAction={this.update}/>
                                                    {
                                                            <ReactTooltip key={answer.uid*2} id={answer.uid.toString()} place="right" type="info" effect="float">
                                                                <span>{answer.tooltip}</span>
                                                            </ReactTooltip>
                                                    }
                                                </React.Fragment>
                                            ))
                                        }
                                    </Column>
                                ))
                            }
                        </div>
                    </div>
                    <div className="taskEdit-area-questions">
                        <Column id={0} name={""}>
                            <div className="taskEdit-column-special-wrapper">
                                {
                                    this.state.variants.filter((item) => item.curr_answer === 0).map((answer) => (
                                        <React.Fragment>
                                            <RequirementItem key={answer.uid} id={answer.uid} name={answer.displayName} answers={{curr_answer:answer.curr_answer, real_answer:answer.curr_answer, check:this.state.check}} onDropAction={this.update}/>
                                            {
                                                            <ReactTooltip key={answer.uid*2} id={answer.uid.toString()} place="right" type="info" effect="float">
                                                                <span>{answer.tooltip}</span>
                                                            </ReactTooltip>
                                            }
                                        </React.Fragment>
                                    ))
                                }
                            </div>
                        </Column>
                        {
                            <Bin></Bin>
                        }
                    </div>
                </DndProvider>
            </div>
        )
    }
}

export default withRouter(TaskAdd); 