import React from "react";
import UserStore from './../stores/User/UserStore';

export default class UserDetailScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            item: null
        }

        this.loadItem = this.loadItem.bind(this);

        // init
        UserStore.addChangeListener(this.loadItem);
        this.loadItem();
    }

    componentWillUnmount() {
        UserStore.removeChangeListener(this.loadItem);
    }

    loadItem() {
        UserStore.getItemById(this.props.match.params.id).then(item => {
            this.setState({
                item: item
            })
        });
    }

    render() {

        const { item } = this.state;

        return item && (
            <div>
                <h2>{item.getName()}</h2>

                <p><strong>Email:</strong> {item.getEmail()}</p>
                <p><strong>Phone:</strong> {item.getPhoneNumber()}</p>
                <p><strong>Address:</strong> {item.getAddress()}</p>
            </div>
        );
    }
}