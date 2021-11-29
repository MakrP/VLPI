import React from "react";
import '../css/UserEdit.css';
import { withRouter } from "react-router";

class UserEdit extends React.Component 
{
    constructor(props) {
        super(props)
        this.onDoneClick = this.onDoneClick.bind(this)
        this.onBackClick = this.onBackClick.bind(this)

        let userId = this.props.match.params.userId;
        this.state = {
            uid: userId,
            user: {
                name: "",
                surname: "",
                email: "",
                password: "",
                birthday: ""
            },
            error: ""
        }
      }

    componentDidMount = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/users/' + this.state.uid + '/full');
        const data = await response.json();

        if (data.errorMessage !== undefined)
        {
            this.props.history.push({pathname:"/login", state: {errorMessage: data.errorMessage}})
        }

        this.setState({ user: data})
    }
    
    onDoneClick = async () => {
        const response = await fetch('http://localhost:8080/vlpi/v1/users/' + this.state.uid, {method:"PUT", credentials:"include", body:JSON.stringify(this.state.user), headers: { 'Content-Type': 'application/json', } });
        const data = await response.json();

        if (data.errorMessage === undefined)
        {
            this.props.history.goBack();
        }
        
        this.setState({error:data.errorMessage});
    }

    onBackClick = () => {
        this.props.history.goBack()
    }

    render() {
        return (
            <div className="useredit-area-main">
                <div className="useredit-form">
                        <div className="invalidus-cont-useredit">
                            User Edit
                        </div>
                        <input className="form-control" value={this.state.user.name} placeholder="Name" onChange={ (event) => {
                            var curr_user = {...this.state.user}
                            curr_user.name = event.target.value;
                            this.setState({ user:curr_user })
                        }}/>
                        <input className="form-control" value={this.state.user.surname} placeholder="Surname" onChange={ (event) => {
                            var curr_user = {...this.state.user}
                            curr_user.surname = event.target.value;
                            this.setState({ user:curr_user })
                        }}/>
                        <input className="form-control" type="date" value={this.state.user.birthday} onChange={ (event) => {
                            var curr_user = {...this.state.user}
                            curr_user.birthday = event.target.value;
                            this.setState({ user:curr_user })
                        }}/>
                        <input className="form-control" value={this.state.user.email} placeholder="Email" onChange={ (event) => {
                            var curr_user = {...this.state.user}
                            curr_user.email = event.target.value;
                            this.setState({ user:curr_user })
                        }}/>
                        <input className="form-control" value={this.state.user.password} placeholder="Password" onChange={ (event) => {
                            var curr_user = {...this.state.user}
                            curr_user.password = event.target.value;
                            this.setState({ user:curr_user })
                        }}/>
                        <div className="userEditActionBtns">
                            <button className="btn btn-success" onClick={this.onDoneClick}>Done</button>
                            <button className="btn btn-primary" onClick={this.onBackClick}>Back</button>
                        </div>
                </div>
            </div>
        )
    }
}

export default withRouter(UserEdit); 