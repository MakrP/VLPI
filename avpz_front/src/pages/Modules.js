import React from "react";
import '../css/Modules.css';
import { withRouter } from "react-router";

class Modules extends React.Component 
{
    constructor(props)
    {
        super(props)
        this.onReqClick = this.onReqClick.bind(this)
    }

    onReqClick = () => {
        this.props.history.push("/modules/req/tasks")
    }

    render() {
        return (
            <div className="modules-area-main">
                <div className="modulesItems">
                    <div className="btn modulesItem" onClick={this.onReqClick}>
                        <i className="fas fa-chart-bar"></i>
                        <span>Requirements Analysis</span>
                    </div>
                    <div className="btn modulesItem">
                        <i className="fas fa-pallet"></i>
                        <span>Modelling</span>
                    </div>
                    <div className="btn modulesItem">
                        <i className="fas fa-code"></i>
                        <span>Coding</span>
                    </div>
                    <div className="btn modulesItem">
                        <i className="fas fa-vial"></i>
                        <span>Testing</span>
                    </div>
                    <div className="btn modulesItem">
                        <i className="fas fa-cog"></i>
                        <span>Accompaniment</span>
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Modules); 