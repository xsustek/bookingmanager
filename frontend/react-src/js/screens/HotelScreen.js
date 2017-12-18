import React from "react";
import HotelStore from './../stores/Hotel/HotelStore';
import HotelList from './../components/HotelList';

export default class HotelScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            items: null
        }

        this.loadItems = this.loadItems.bind(this);

        // init
        HotelStore.addChangeListener(this.loadItems);
        this.loadItems();
    }

    componentWillUnmount() {
        HotelStore.removeChangeListener(this.loadItems);
    }

    loadItems() {
        HotelStore.getAllItems().then(items => {
            this.setState({
                items: items
            })
        });
    }

    render() {

        const { items } = this.state;

        return (
            <div>
                <h2>Hotel</h2>

                {!items && <p>NIC</p>}
                {items && <HotelList items={items} />}
            </div>
        );
    }
}