import React from "react";
import {HashRouter as Router, Link, Redirect, Route, withRouter} from 'react-router-dom'

import AppStore from './../stores/AppStore';

import HomeScreen from './../screens/HomeScreen';
import LoginScreen from './../screens/LoginScreen';
import UsersScreen from "../screens/UsersScreen";
import HotelScreen from "../screens/HotelScreen";
import HotelDetailScreen from "../screens/HotelDetailScreen";
import UsersDetailScreen from "../screens/UsersDetailScreen";
import RoomDetailScreen from "../screens/RoomDetailScreen";
import ReservationScreen from "../screens/ReservationScreen";

const SignOutButton = withRouter(({ history }) => {
    return (
        <a onClick={() => {
            AppStore.signout();
            history.push('/');
        }} style={{cursor: 'pointer'}}>Sign out</a>
    );
});

const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        AppStore.isSignedIn() ? (
            <Component {...props} />
        ) : (
                <Redirect to={{
                    pathname: '/login',
                    state: { from: props.location }
                }} />
            )
    )} />
);

export default class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            authUser: AppStore.getAuthUser()
        };

        this.init = this.init.bind(this);

        // Initialization
        AppStore.addChangeListener(this.init);
        this.init();
    }

    init() {
        this.setState({
            authUser: AppStore.getAuthUser()
        })
    }

    componentWillUnmount() {
        AppStore.removeChangeListener(this.init);
    }

    render() {

        const { authUser } = this.state;

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
                                <Link className="navbar-brand" to="/">Bookingmanager</Link>
                            </div>

                            <div className="collapse navbar-collapse">

                                <ul className="nav navbar-nav">
                                    <li><Link to="/reservations">Reservations</Link></li>
                                    <li><Link to="/hotels">Hotels</Link></li>
                                    <li><Link to="/users">Users</Link></li>
                                </ul>

                                <ul className="nav navbar-nav navbar-right">
                                    {!!authUser
                                        ? [
                                            <p className="text-muted navbar-text" key='welcome'>Signed in as {authUser.getName()}</p>,
                                            <li key='logout'><SignOutButton /></li>
                                        ]
                                        : <li><Link to="/login">Sign In</Link></li>}
                                </ul>
                            </div>
                        </div>
                    </nav>
                    <div className="container">
                        <Route exact path="/" component={HomeScreen} />
                        <Route exact path="/reservations" component={ReservationScreen} />
                        <PrivateRoute exact path="/hotels" component={HotelScreen} />
                        <PrivateRoute path="/hotels/:id" component={HotelDetailScreen} />
                        <PrivateRoute exact path="/users" component={UsersScreen} />
                        <PrivateRoute path="/users/:id" component={UsersDetailScreen} />
                        <PrivateRoute path="/rooms/:id" component={RoomDetailScreen} />
                        {/* <Route path="/about" component={AboutScreen} /> */}

                        <Route path="/login" component={LoginScreen} />
                    </div>
                </div>
            </Router>
        );
    }
}