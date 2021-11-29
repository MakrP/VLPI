import React from "react";
import '../css/TaskTableElement.css'
import { withRouter } from "react-router";

class TaskTableElement extends React.Component
{
    constructor(props)
    {
        super(props)
        this.onClickDel = this.onClickDel.bind(this);
        this.onClickEdit = this.onClickEdit.bind(this);
        /* TODO */
        //GET USER INFO HERE FROM -> this.props.info.added_by_user_id
    }

    onClickDel = async () => {
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/tasks/' + this.props.info.uid, {method: 'DELETE', credentials:"include"});
        const response_fetched = await response_tasks.text();
        
        this.props.reRender({msg:response_fetched, code: response_tasks.status})
    }

    onClickEdit = () => {
        this.props.history.push("/tasks/edit/" + this.props.info.uid)
    }

    render() {
        return (
            <tr>
                <td>{this.props.info.uid}</td>
                <td>{this.props.info.displayName}</td>
                <td>{this.props.info.moduleDisplayName}</td>
                <td>{this.props.info.topicDisplayName}</td>
                <td className="taskAction">
                    <div className="btn btn-primary" onClick={this.onClickEdit}>Edit</div>
                    <div className="btn btn-danger" onClick={this.onClickDel}>Del</div> 
                </td>
            </tr>
        )
    }
}

export default withRouter(TaskTableElement);