import React from "react";
import { Route, Switch, withRouter } from "react-router";
import '../css/WorkingSpace.css';
import Profile from "./Profile";
import Modules from "./Modules";
import Users from "./Users";
import Tasks from "./Tasks";
import UserEdit from "./UserEdit";
import ModuleTasks from "./ModuleTasks";
import TaskRun from "./TaskRun";
import TaskAdd from "./TaskAdd";
import TaskEdit from "./TaskEdit";
import TaskComplete from "./TaskComplete";
import Statistic from "./Statistic";

class WorkingSpace extends React.Component 
{
    constructor(props)
    {
        super(props)
        this.state = {userName:"", role:""}
        this.onClickProfile = this.onClickProfile.bind(this)
        this.onClickStudy = this.onClickStudy.bind(this)
        this.onClickUsers = this.onClickUsers.bind(this)
        this.onClickTasks = this.onClickTasks.bind(this)
    }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/users/main', {credentials: 'include'});
        const data = await response.json();

        if (data.errorMessage !== undefined)
        {
            this.props.history.push({pathname:"/login", state: {errorMessage: data.errorMessage}})
        }

        this.setState({ userName: data.userName, role: data.role })
    }

    onClickProfile = () => {
        this.props.history.push("/profile")
    }

    onClickStudy = () => {
        this.props.history.push("/modules")
    }

    onClickUsers = () => {
        this.props.history.push("/users")
    }

    onClickTasks = () => {
        this.props.history.push("/tasks")
    }

    onClickStatistic = () => {
        this.props.history.push('/statistic')
    }

    onClickLogout = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/users/logout', {credentials:"include"});
        this.props.history.push("/login")
    }

    render() {
        return (
            <div className="workspace-area-main row">
                <div className="workspace-menu col-2">
                    <div className="workspace-menu-wrapper">
                        <div className="menuProfile">
                            <i className="fas fa-user-circle" onClick={this.onClickProfile}></i>
                            <span>{this.state.userName}</span>
                        </div>
                        <div className="menuItems">
                            <div className="btn menuItem" onClick={this.onClickStudy}>
                                <i className="fas fa-graduation-cap"></i>
                                <span>Studying</span>
                            </div>
                            {
                                this.state.role === "ADMIN" ?
                                <React.Fragment>
                                    <div className="btn menuItem" onClick={this.onClickTasks}>
                                        <i className="fas fa-clipboard-check"></i>
                                        <span id="taskMngSpan">Task Management</span>
                                    </div>
                                    <div className="btn menuItem" onClick={this.onClickUsers}>
                                        <i className="fas fa-user-cog"></i>
                                        <span>User Management</span>
                                    </div>
                                    <div className="btn menuItem" onClick={this.onClickStatistic}>
                                        <i class="fas fa-chart-pie"></i>
                                        <span>Statistic</span>
                                    </div>
                                </React.Fragment> : null
                            }
                        </div>
                    </div>
                    <div className="menuItem suck btn" onClick={this.onClickLogout}>
                        <i class="fas fa-sign-out-alt"></i>
                        <span>Logout</span>
                    </div>
                </div>
                <div className="workspace-main-area col-10">
                    <Switch>
                        <Route exact path="/profile" component={Profile} />
                        <Route exact path="/statistic" component={Statistic} />
                        
                        <Route exact path="/modules" component={Modules} />
                        <Route path="/modules/:module/tasks/complete/:taskId" component={TaskComplete}/>
                        <Route path="/modules/:module/tasks/:taskId" component={TaskRun} />
                        <Route path="/modules/:module/tasks" component={ModuleTasks} />

                        <Route exact path="/users" component={Users} />
                        <Route path="/users/edit/:userId" component={UserEdit}/>
                        
                        <Route exact path="/tasks" component={Tasks} />
                        <Route path="/tasks/add" component={TaskAdd}/>
                        <Route path="/tasks/edit/:taskId" component={TaskEdit}/>
                    </Switch>
                </div>
            </div>
        )
    }
}

export default withRouter(WorkingSpace); 