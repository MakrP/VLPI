import React from "react";
import '../css/ModuleTaskElement.css'
import { withRouter } from "react-router";

class StatusComponent extends React.Component
{
    render() {
        let elem;
        
        switch(this.props.status)
        {
            case "Not started":
                elem = <div className="statusElem statusNotStarted">Not Started</div>;
                break;
            case "Started":
                elem = <div className="statusElem statusStarted">Started</div>;
                break;
            case "Finished":
                elem = <div className="statusElem statusFinished">Finished</div>;
                break;
            default:
                elem = <div>Error</div>
                break;
        }
        return (
            <div className="statusWrapper">
                {elem}
            </div>
        )
    }
}

/* TODO */
//Remove disable from select

class ModuleTaskElement extends React.Component
{
    constructor(props)
    {
        super(props)
        this.onClickStart = this.onClickStart.bind(this)
        this.state = {
            level: "Easy"
        }
    }

    onClickStart = () => {
        let module = this.props.match.params.module
        this.props.history.push("/modules/" + module + "/tasks/" + this.props.info.uid + "?level=" + this.state.level.toUpperCase())
    }

    render() {
        return (
            <tr>
                <td>{this.props.info.uid}</td>
                <td>{this.props.info.displayName}</td>
                <td><StatusComponent status={this.props.info.taskStatus} /></td>
                <td>{this.props.info.topicDisplayName}</td>
                <td>{this.props.info.level}</td>
                <td>{this.props.info.completionDate}</td>
                <td>{this.props.info.grade}</td>
                <td className="moduleTaskAction">
                    <select defaultValue={this.defaultValue} onChange={(event => {this.setState({level: event.target.value});})}>
                        <option>Easy</option>
                        <option>Normal</option>
                        <option>Hard</option>
                    </select>
                    <div className="btn btn-success" onClick={this.onClickStart}>Start</div> 
                </td>
            </tr>
        )
    }
}

export default withRouter(ModuleTaskElement);