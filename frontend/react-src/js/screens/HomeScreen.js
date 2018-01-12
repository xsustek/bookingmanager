import React from "react";
import HotelStore from "../stores/Hotel/HotelStore";
import HotelListCustomer from "../components/HotelListCustomer";

export default class HomeScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            hotels: null
        };

        this.loadItems = this.loadItems.bind(this);

        // init
        HotelStore.addChangeListener(this.loadItems);
        this.loadItems();
    }

    loadItems() {
        HotelStore.getAllItems().then(hotels => this.setState({ hotels }))
    }

    render() {

        const { hotels } = this.state;

        return (
            <div>
                <h2>Home</h2>

                <p>
                    Welcome at Booking Manager.
                </p>

                {hotels && <HotelListCustomer items={hotels}/>}
            </div>
        );
    }
}