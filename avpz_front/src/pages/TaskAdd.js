import React from "react";
import { DndProvider, useDrag, useDrop } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import '../css/TaskAdd.css';
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
        <div data-tip data-for={data_for} ref={drag} style={reqItemStyle} className={props.answers.curr_answer !== 0 ? "taskAdd-variant" : "taskAdd-variant-special"}>
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
        <div ref={drop} style={colItemStyle} className={id !== 0 ? "taskAdd-column" : "taskAdd-column-special"}>
            <div className="taskAdd-column-action">
                {props.name}
                <div className="taskAdd-column-action-del">
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
        <div ref={drop} style={colItemStyle} className={"taskRun-remove-bin"}>
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
        let curr_variants = [...this.state.variants]
        curr_variants.map((value) => {
            let curr_answer = value.curr_answer
            value.curr_answer = {displayName: curr_answer}
            
            delete value.uid
            return value
        })

        const data = JSON.stringify({variants: curr_variants, topic: this.state.topic, module: this.state.module, name: this.state.name, time: 120});
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/tasks', {method: 'POST', body: data, headers: { 'Content-Type': 'application/json', }});
        
        this.props.history.goBack()
    }

    render() {
        return (
            <div className="taskAdd-area-main">
                <DndProvider backend={HTML5Backend}>
                    <div className="taskAdd-area-answers">
                        <div className="taskAdd-actions">
                            <div className="taskAdd-category">
                                <input id='category' placeholder="Category" className='form-control'/>
                                <button className="btn btn-primary" onClick={this.onCategoryAddClick}>Add</button>
                            </div>
                            <div className="taskAdd-variant-add">
                                <input id='variant-name' placeholder="Requirement" className='form-control'/>
                                <input id='variant-tooltip' placeholder="Help" className='form-control'/>
                                <button className="btn btn-primary" onClick={this.onVariantAddClick}>Add</button>
                            </div>
                            <div className="taskAdd-task-other">
                                <input onChange={(event) => {this.setState({name:event.target.value})}} placeholder="Name" className='form-control'/>
                                <select onChange={(event) => {this.setState({module:event.target.value})}}>
                                    <option>Requirement Analysis</option>
                                    <option>Modelling</option>
                                    <option>Coding</option>
                                    <option>Testing</option>
                                    <option>Accompaniment</option>
                                </select>
                            </div>
                            <div className="taskAdd-area-questions-actions">
                                <button className="btn btn-success" onClick={this.onDoneClick}>Finish</button>
                            </div>
                        </div>
                        <div className="taskAdd-area-categories-add">
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
                    <div className="taskAdd-area-questions">
                        <Column id={0} name={""}>
                            <div className="taskAdd-column-special-wrapper">
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