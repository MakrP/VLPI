import React from "react";
import '../css/ModuleTasks.css';
import ModuleTaskElement from "./ModuleTaskElement";

class ModuleTasks extends React.Component 
{
    constructor(props)
    {
        super(props)

        this.state = {
            tasks_count: 10,
            pages_count: 0,
            current_page: 0,
            tasks: [],

        }
    }

    updateTasks = async () => {
        const response_tasks = await fetch('http://localhost:8080/vlpi/v1/tasks?page=' + this.state.current_page + '&size=' + this.state.tasks_count, {credentials:'include'});
        const data_tasks = await response_tasks.json();

        // data_tasks.map((value) => {
        //     const response_result = fetch('http://localhost:8080/vlpi/v1/tasks/result/' + value.uid);
        //     const data_tasks_result = response_result.json();
        //     value.completionDate = data_tasks_result.completionDate
        //     value.grade = data_tasks_result.grade
        //     return value
        // })

        this.setState({ pages_count: this.state.pages_count, tasks: data_tasks})
    }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/tasks/pages?size=' + this.state.tasks_count, {credentials:'include'});
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

    render() {
        return (
            <div className="modules-tasks-area-main">
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Name</th>
                            <th scope="col">Status</th>
                            <th scope="col">Topic</th>
                            <th scope="col">Level</th>
                            <th scope="col">Completion Date</th>
                            <th scope="col">Grade</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.tasks.map((value, index) => {
                                return <ModuleTaskElement key={value.uid} info={value} />
                            })
                        }
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

export default ModuleTasks;