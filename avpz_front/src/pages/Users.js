import React from "react";
import UserTableElement from "./UserTableElement";
import '../css/Users.css';

class Users extends React.Component 
{
    constructor(props)
    {
        super(props)

        /* TODO */
        // Logic + Remove Hardcore
        this.state = {
            user_count: 10,
            pages_count: 0,
            current_page: 0,
            users: [],
            errorFromDelete: {msg:"", code: 0}
        }
    }

    reRender = (response) => {
        this.setState({errorFromDelete:response}, () => {this.updateUsers()});
    }

    updateUsers = async () => {
        const response_users = await fetch('http://localhost:8080/vlpi/v1/users?page=' + this.state.current_page + '&size=' + this.state.user_count);
        const data_users = await response_users.json();

        this.setState({ pages_count: this.state.pages_count, users: data_users})
    }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/users/pages?size=' + this.state.user_count);
        const data = await response.json();
        
        if (data.errorMessage !== undefined)
        {
            this.props.history.push({pathname:"/login", state: {errorMessage: data.errorMessage}})
        }

        this.setState({pages_count: data},  () => {this.updateUsers()})
    }

    onSetPrevious = () => {
        let curr_page = this.state.current_page
        if (curr_page - 1 < 0)
        {
            return
        }

        this.setState({current_page: curr_page - 1}, () => {this.updateUsers()} )
    }

    onSetNext = () => {
        let curr_page = this.state.current_page
        if (curr_page + 1 === this.state.pages_count)
        {
            return
        }

        this.setState({current_page: curr_page + 1}, () => {this.updateUsers()})
    }

    render() {
        return (
            <div className="users-area-main">
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Email</th>
                            <th scope="col">Name</th>
                            <th scope="col">Surname</th>
                            <th scope="col">Birthday</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.users.map((value, index) => {
                                return <UserTableElement key={value.uid} reRender={this.reRender} info={value} />
                            })
                        }
                    </tbody>
                </table>
                <div className="users-area-pagination">
                    <div className="users-area-pagination-wrapper">
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

export default Users;