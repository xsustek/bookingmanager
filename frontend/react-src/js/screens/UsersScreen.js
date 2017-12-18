import React from "react";
import UserStore from "../stores/User/UserStore";

export default class UsersScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            error: false,
            redirectToReferrer: false,
            users: []
        };

    }
    
    componentWillMount() {
        var users = UserStore.getAllItems();
    }

    render() {
        return (
            <div>
                <h2>Users</h2>
            </div>
        );
    }
}