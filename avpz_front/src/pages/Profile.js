import React from "react";
import '../css/Profile.css';

class Profile extends React.Component 
{
    constructor(props)
    {
        super(props)
        this.state = {user: {
                name: "", 
                surname: "",
                email: "",
                birthday: "",
                password: ""
            },
            statistics: {
                totalTaskComplete: "0",
                totalTimeOnTasks: "0",
                averageTimeOnTasks: "0",
                averageMark: "0"
            }
        }
        this.onClickEdit = this.onClickEdit.bind(this);
    }

    onClickEdit = () => {
        this.props.history.push("/users/edit/" + this.state.user.uid)
    }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/users/full', {credentials: 'include'});
        const response_statistic = await fetch('http://localhost:8080/vlpi/v1/statistic', {credentials: 'include'});
        const data = await response.json();
        const data_statistic = await response_statistic.json();

        if (response_statistic.status === 404)
        {
            this.setState({ user: data })
            return
        }

        if (data.errorMessage !== undefined || data_statistic.errorMessage !== undefined)
        {
            this.props.history.push({pathname:"/login", state: {errorMessage: data.errorMessage}})
        }

        this.setState({ user: data, statistics: data_statistic })
    }


    render() {
        return (
            <div className="profile-area-main row">
                <div className="profile-info-show col-6">
                    <div className="profile-display-info">
                        <i className="fas fa-user-circle"></i>
                        <div className="profileItems">
                            <div className="profileItem">
                                <span className="profileItemName">Name:</span>
                                <span>{this.state.user.name}</span>
                            </div>
                            <div className="profileItem">
                                <span className="profileItemName">Surname:</span>
                                <span>{this.state.user.surname}</span>
                            </div>
                            <div className="profileItem">
                                <span className="profileItemName">Email:</span>
                                <span>{this.state.user.email}</span>
                            </div>
                            <div className="profileItem">
                                <span className="profileItemName">Birthday:</span>
                                <span>{this.state.user.birthday}</span>
                            </div>
                            <div className="btn btn-primary w-25 m-3" onClick={this.onClickEdit}>Edit</div>
                        </div>
                    </div>
                </div>
                <div className="profile-statistic-show col-6">
                    <div className="profile-display-statistic">
                        <div className="statisticsItems">
                            <h1 className="h1">Statistics</h1>
                            <div className="statisticsItem">
                                <span className="statisticsItemName">Total tasks complete:</span>
                                <span>{this.state.statistics.totalTaskComplete}</span>
                            </div>
                            <div className="statisticsItem">
                                <span className="statisticsItemName">Total time on tasks:</span>
                                <span>{this.state.statistics.totalTimeOnTasks}</span>
                            </div>
                            <div className="statisticsItem">
                                <span className="statisticsItemName">Average time on task:</span>
                                <span>{this.state.statistics.averageTimeOnTasks}</span>
                            </div>
                            <div className="statisticsItem">
                                <span className="statisticsItemName">Average mark:</span>
                                <span>{this.state.statistics.averageMark}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Profile; 