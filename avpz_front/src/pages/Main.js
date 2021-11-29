import React from "react";
import '../css/Main.css';
import Login from "./Login";
import {Switch, Route, BrowserRouter} from 'react-router-dom';
import Register from "./Register";
import WorkingSpace from "./WorkingSpace";

class MainArea extends React.Component 
{
    render() {
        return (
            <div className="main-area-main">
                <BrowserRouter>
                    <Switch>
                        <Route exact path="/login" component={Login} />
                        <Route exact path="/register" component={Register} />
                        <Route path="/" component={WorkingSpace} />
                    </Switch>
                </BrowserRouter>
            </div>
        )
    }
}

export default MainArea; 