import React from "react";
import '../css/UserTableElement.css'
import { withRouter } from "react-router";

class UserTableElement extends React.Component
{
    constructor(props)
    {
        super(props)
        this.onClickDel = this.onClickDel.bind(this);
        this.onClickEdit = this.onClickEdit.bind(this);
    }

    onClickDel = async () => {
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/users/' + this.props.info.uid, {method: 'DELETE'});
        const response_fetched = await response_tasks.text();
        
        this.props.reRender({msg:response_fetched, code: response_tasks.status})
    }

    onClickEdit = () => {
        this.props.history.push("/users/edit/" + this.props.info.uid)
    }

    render() {
        return (
            <tr>
                <td>{this.props.info.uid}</td>
                <td>{this.props.info.email}</td>
                <td>{this.props.info.name}</td>
                <td>{this.props.info.surname}</td>
                <td>{this.props.info.birthday}</td>
                <td className="userAction">
                    <div className="btn btn-primary" onClick={this.onClickEdit}>Edit</div>
                    <div className="btn btn-danger" onClick={this.onClickDel}>Del</div> 
                </td>
            </tr>
        )
    }
}

export default withRouter(UserTableElement);