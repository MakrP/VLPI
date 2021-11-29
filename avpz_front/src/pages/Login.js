import React from "react";
import { Link } from "react-router-dom";
import '../css/Login.css';
import { withRouter } from "react-router";

class Login extends React.Component 
{
    constructor(props) {
        super(props)
        this.onLoginClick = this.onLoginClick.bind(this)

        /* TODO */
        //Error should be object with 
        //{name_err: 1/0, password_err: 1/0 ...etc}
        this.state = {login: "", password: "", error: ""};
    }

    onLoginClick = async () => {
        let data = {...this.state};
        delete data.error;

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', },
            body: JSON.stringify(data),
            credentials: 'include'
        };
        const response = await fetch('http://localhost:8080/vlpi/v1/users/authorize', requestOptions);
        const response_fetched = await response.json();

        if (response_fetched.errorMessage === undefined)
        {
            this.props.history.push("/modules")
            return;
        }

        this.setState({error: response_fetched.errorMessage})
    }

    render() {
        return (
            <div className="login-area-main">
                <div className="login-form">
                        <div className="invalidus-cont">
                            <i className="far fa-user-circle"></i>
                        </div>
                        <input className="form-control" onChange={ (event)=>{this.setState({login: event.target.value}); }} placeholder="Email"/>
                        <input className="form-control" onChange={ (event)=>{this.setState({password: event.target.value}); }} placeholder="Password"/>
                        <button className="btn btn-primary" onClick={this.onLoginClick}>Login</button>
                        <div className="invalidus-text">
                            <span>Don’t have an account?</span>
                            <span><Link className="btn-link text-decoration-none" to="/register">     Sign Up</Link></span>
                        </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Login); 