import React from "react";
import AppStore from "../stores/AppStore";
import {
    Redirect
} from 'react-router-dom'

export default class LoginScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            error: false,
            redirectToReferrer: false
        };

        this.handleSignIn = this.handleSignIn.bind(this);
    }

    handleSignIn() {
        const result = AppStore.signin({
            email: '',
            password: ''
        });

        console.log(result);
        if(result) {
            this.setState({
                error: false,
                redirectToReferrer: true
            });
        } else {
            this.setState({
                error: true,
                redirectToReferrer: false
            });
        }
    }

    render() {

        const { from } = this.props.location.state || { from: { pathname: '/' } };
        const { error, redirectToReferrer } = this.state;

        console.log(from);

        if (redirectToReferrer) {
            return (
                <Redirect to={from}/>
            )
        }

        return (
            <div className='login-screen'>
                <div className="row">
                    <div className="col-md-6 col-md-offset-3">

                        <h1 className="text-center">Sign In</h1>

                        <form className="form-horizontal" method="POST" action="#">
                            <div className="form-group">
                                <label htmlFor="email">E-mail address</label>

                                <div className="input-group">
                                <span className="input-group-addon">
                                    <i className="fa fa-user"></i>
                                </span>
                                    <input id="email" type="email" className="form-control" name="email" placeholder="E-mail address"/>
                                </div>

                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Password</label>

                                <div className="input-group">
                                <span className="input-group-addon">
                                    <i className="fa fa-lock"></i>
                                </span>
                                    <input id="password" type="password" className="form-control" name="password" placeholder="Password"/>
                                </div>

                            </div>

                            <div className="form-group">
                                <button type="submit" className="btn btn-primary" onClick={this.handleSignIn}>
                                    <i className="fa fa-btn fa-sign-in"></i> Sign In
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}