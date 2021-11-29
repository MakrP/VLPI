import React from "react";
import TaskTableElement from "./TaskTableElement";
import '../css/Tasks.css';
import { withRouter } from "react-router";

class Tasks extends React.Component 
{
    constructor(props)
    {
        super(props)
        this.onClickAdd = this.onClickAdd.bind(this)

        /* TODO */
        // Logic + Remove Hardcore
        this.state = {
            tasks_count: 10,
            pages_count: 0,
            current_page: 0,
            errorFromDelete: {},
            tasks: []
        }
    }

    reRender = (response) => {
        this.setState({errorFromDelete:response}, () => {this.updateTasks()});
    }

    updateTasks = async () => {
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/tasks/admin?page=' + this.state.current_page + '&size=' + this.state.tasks_count);
        const data_tasks = await response_tasks.json();

        this.setState({ pages_count: this.state.pages_count, tasks: data_tasks})
    }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/tasks/pages?size=' + this.state.tasks_count);
        const data = await response.json();
        
        if (data.errorMessage !== undefined)
        {
            this.props.history.push({pathname:"/login", state: {errorMessage: data.errorMessage}})
        }

        this.setState({pages_count: data},  () => {this.updateTasks()})
    }

    onSetPrevious = () => {
        let curr_page = this.state.current_page
        if (curr_page - 1 < 0)
        {
            return
        }

        this.setState({current_page: curr_page - 1}, () => {this.updateTasks()} )
    }

    onSetNext = () => {
        let curr_page = this.state.current_page
        if (curr_page + 1 === this.state.pages_count)
        {
            return
        }

        this.setState({current_page: curr_page + 1}, () => {this.updateTasks()})
    }

    onClickAdd = () => {
        this.props.history.push("/tasks/add");
    }

    render() {
        return (
            <div className="tasks-area-main">
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Name</th>
                            <th scope="col">Module</th>
                            <th scope="col">Topic</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.tasks.map((value, index) => {
                                return <TaskTableElement reRender={this.reRender} key={value.uid} info={value} />
                            })
                        }
                        <tr>
                            <td className="taskAdd"></td>
                            <td className="taskAdd"></td>
                            <td className="taskAdd"></td>
                            <td className="taskAdd"></td>
                            <td className="taskAdd">
                                <div className="btn btn-success" onClick={this.onClickAdd}>Add</div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div className="tasks-area-pagination">
                    <div className="tasks-area-pagination-wrapper">
                        <nav aria-label="Page navigation example">
                            <ul class="pagination">
                                <li class="page-item"><button class="page-link" onClick={this.onSetPrevious}>Previous</button></li>
                                <li class="page-item"><button class="page-link" onClick={this.onSetNext}>Next</button></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Tasks);