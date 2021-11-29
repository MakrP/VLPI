import React from "react";
import '../css/TaskComplete.css';
import { withRouter } from "react-router";

class TaskComplete extends React.Component 
{
    constructor(props)
    {
        super(props)
    }
    
    onDoneClick = () => {
        this.props.history.push('/modules/' + this.props.match.params.module + '/tasks')
    }

    render() {
        return (
            <div className="taskComplete-area-main">
                <div className="taskComplete-area-wrapper">
                    <div className="taskComplete-area-wrapper-name">
                        Well Done!
                    </div>
                    <div className="taskComplete-area-wrapper-score">
                        {this.props.location.state.score}/100
                    </div>
                    <div className="taskComplete-area-wrapper-time">
                        Time passed: {this.props.location.state.time}  
                    </div>
                    <div  className="taskComplete-area-wrapper-btn">
                        <button onClick={this.onDoneClick} className="btn btn-primary">Done</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(TaskComplete); 