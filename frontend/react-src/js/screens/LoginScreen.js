import React from "react";
import AppStore from "../stores/AppStore";
import {Redirect} from 'react-router-dom'

export default class LoginScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            error: false,
            redirectToReferrer: false,
            formEmail: 'karel@mail.com',
            formPassword: 'password',
        };

        this.handleSignIn = this.handleSignIn.bind(this);
    }

    async handleSignIn(e) {
        e.preventDefault();

        const result = await AppStore.signin({
            email: this.state.formEmail,
            password: this.state.formPassword
        });

        if (result) {
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
        const { error, redirectToReferrer, formEmail, formPassword } = this.state;

        if (redirectToReferrer) {
            return (
                <Redirect to={from} />
            )
        }

        return (
            <div className='login-screen'>
                <div className="row">
                    <div className="col-md-6 col-md-offset-3">

                        <h1 className="text-center">Sign In</h1>

                        {error && (
                            <div className="alert alert-dismissible alert-danger">
                                <strong>Wrong email or password!</strong>
                            </div>
                        )}

                        <form className="form-horizontal" method="POST" onSubmit={this.handleSignIn}>
                            <div className="form-group">
                                <label htmlFor="email">E-mail address</label>

                                <div className="input-group">
                                    <span className="input-group-addon">
                                        <i className="fa fa-user"></i>
                                    </span>
                                    <input
                                        id="email"
                                        type="email"
                                        className="form-control"
                                        name="email"
                                        placeholder="E-mail address"
                                        onChange={e => this.setState({ formEmail: e.target.value })}
                                        value={formEmail}
                                    />
                                </div>

                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Password</label>

                                <div className="input-group">
                                    <span className="input-group-addon">
                                        <i className="fa fa-lock"></i>
                                    </span>
                                    <input
                                        id="password"
                                        type="password"
                                        className="form-control"
                                        name="password"
                                        placeholder="Password"
                                        onChange={e => this.setState({ formPassword: e.target.value })}
                                        value={formPassword}
                                    />
                                </div>
                            </div>

                            <div className="form-group">
                                <button type="submit" className="btn btn-primary">
                                    <i className="fa fa-btn fa-sign-in"></i> Sign In
                                </button>
                            </div>

                            <div className="form-group">
                                <p className="help-block"><small>sample users</small></p>
                                <p className="help-block"><strong>ADMIN:</strong><br/>karel@mail.com<br/>password</p>
                                <p className="help-block"><strong>USER:</strong><br/>ivan@mail.com<br/>password</p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}