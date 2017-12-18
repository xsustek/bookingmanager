import React from "react";
import {
    HashRouter as Router,
    Route,
    Link,
    Redirect,
    withRouter
} from 'react-router-dom'

import AppStore from './../stores/AppStore';

import HomeScreen from './../screens/HomeScreen';
import AboutScreen from './../screens/AboutScreen';
import AdminScreen from './../screens/AdminScreen';
import LoginScreen from './../screens/LoginScreen';

const SignOutButton = withRouter(({ history }) => {
    return (
        <a onClick={() => {
            AppStore.signout();
            history.push('/');
        }}>Sign out</a>
    );
});

const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        AppStore.isSignedIn() ? (
            <Component {...props}/>
        ) : (
            <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
            }}/>
        )
    )}/>
);

export default class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isSignedIn: false
        };

        this.init = this.init.bind(this);

        // Initialization
        AppStore.addChangeListener(this.init);
        this.init();
    }

    init() {
        this.setState({
            isSignedIn: AppStore.isSignedIn()
        });
    }

    componentWillUnmount() {
        AppStore.removeChangeListener(this.init);
    }

    render() {

        const { isSignedIn } = this.state;

        return (
            <Router>
                <div>
                    <nav className="navbar navbar-default navbar-fixed-top">
                        <div className="container">
                            <div className="navbar-header">
                                <button type="button" className="navbar-toggle collapsed">
                                    <span className="sr-only">Toggle navigation</span>
                                    <span className="icon-bar"></span>
                                    <span className="icon-bar"></span>
                                    <span className="icon-bar"></span>
                                </button>
                                <a className="navbar-brand" href="#">Bookingmanager</a>
                            </div>

                            <div className="collapse navbar-collapse">
                                <ul className="nav navbar-nav">
                                    <li><Link to="/">Home</Link></li>
                                    <li><Link to="/admin">Administration</Link></li>
                                    <li><Link to="/about">About</Link></li>
                                </ul>
                                <ul className="nav navbar-nav navbar-right">
                                    {isSignedIn
                                        ? <li><SignOutButton/></li>
                                        : <li><Link to="/login">Sign In</Link></li>}
                                </ul>
                            </div>
                        </div>
                    </nav>
                    <div className="container">
                        <Route exact path="/" component={HomeScreen}/>
                        <PrivateRoute path="/admin" component={AdminScreen}/>
                        <Route path="/about" component={AboutScreen}/>

                        <Route path="/login" component={LoginScreen}/>
                    </div>
                </div>
            </Router>
        );
    }
}