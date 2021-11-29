import React from "react";
import { Link } from "react-router-dom";
import '../css/Register.css';
import { withRouter } from "react-router";

class Register extends React.Component 
{
    constructor(props) {
        super(props)
        this.onRegisterClick = this.onRegisterClick.bind(this)
        this.state = {
            name: "",
            surname: "",
            email: "",
            login: "",
            password: "",
            birthDate: "",
            error: ""
        }
      }

    onRegisterClick = async () => {
        let data = {...this.state};
        delete data.error;

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };

        const response = await fetch('http://localhost:8080/vlpi/v1/users/register', requestOptions);
        const response_fetched = await response.json();

        if (response_fetched.errorMessage === undefined)
        {
            this.props.history.push("/login")
            return;
        }

        this.setState({error: response_fetched.errorMessage})
    }

    render() {
        return (
            <div className="register-area-main">
                <div className="register-form">
                        <div className="invalidus-cont-reg">
                            <i className="far fa-user-circle"></i>
                        </div>
                        <input className="form-control" placeholder="Name" onChange={ (event) => {this.setState({ name:event.target.value })} }/>
                        <input className="form-control" placeholder="Surname" onChange={ (event) => {this.setState({ surname:event.target.value })} }/>
                        <input className="form-control" type="date" placeholder="Birthday" onChange={ (event) => {this.setState({ birthDate:event.target.value })} }/>
                        <input className="form-control" placeholder="Email" onChange={ (event) => {this.setState({ email:event.target.value, login: event.target.value})} }/>
                        <input className="form-control" placeholder="Password" onChange={ (event) => {this.setState({ password:event.target.value })} }/>
                        <button className="btn btn-primary" onClick={this.onRegisterClick}>Register</button>
                        <div className="invalidus-text-reg">
                            <span>Have an account?</span>
                            <span><Link className="btn-link text-decoration-none" to="/login">     Sign In</Link></span>
                        </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Register); 