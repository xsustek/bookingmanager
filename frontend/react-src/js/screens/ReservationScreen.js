import React from "react";
import ReservationStore from "../stores/Reservation/ReservationStore";
import ReservationList from "../components/ReservationList";

export default class ReservationScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            reservations: null
        };

        this.loadItems = this.loadItems.bind(this);

        ReservationStore.addChangeListener(this.loadItems);

        this.loadItems();
    }

    loadItems() {
        ReservationStore.getUserReservationItem().then(d => {
            this.setState({
                reservations: d
            });
        });
    }

    componentWillUnmount() {
        ReservationStore.removeChangeListener(this.loadItems);
    }

    render() {
        const {reservations} = this.state;
        return reservations && (
            <div>
                <h2>ReservationScreen</h2>

                {reservations && <ReservationList items={reservations} />}
            </div>
        );
    }
}