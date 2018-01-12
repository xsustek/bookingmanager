import React from "react";
import UserStore from './../stores/User/UserStore';
import ReservationStore from "../stores/Reservation/ReservationStore";
import {deleteReservationItem} from "../stores/Reservation/ReservationActions";
import ReservationList from "../components/ReservationList";

export default class UserDetailScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            item: null,
            reservations: null,
            reservationsFiltered: null,
            filterFrom: '',
            filterTo: ''
        };

        this.loadItem = this.loadItem.bind(this);
        this.onRemove = this.onRemove.bind(this);
        this.filter = this.filter.bind(this);

        // init
        UserStore.addChangeListener(this.loadItem);
        ReservationStore.addChangeListener(this.loadItem);
        this.loadItem();
    }

    componentWillUnmount() {
        UserStore.removeChangeListener(this.loadItem);
        ReservationStore.removeChangeListener(this.loadItem);
    }

    loadItem() {
        UserStore.getItemById(this.props.match.params.id).then(item => {
            this.setState({
                item: item
            })
        });

        ReservationStore.getAllItems().then(reservations => {
            this.setState((prev) => ({
                reservations: reservations
                    .filter(reservation => reservation.getUser().id == this.props.match.params.id),
                reservationsFiltered: reservations
                    .filter(reservation => reservation.getUser().id == this.props.match.params.id)
            }));
        });
    }

    onRemove(item) {
        deleteReservationItem({
            id: item.id
        });
    }



    filter(e) {
        e.preventDefault();

        const res = this.state.reservations
            .filter(reservation => dateToStr(reservation.startTime) >= this.state.filterFrom)
            .filter(reservation => dateToStr(reservation.endTime) <= this.state.filterTo || !this.state.filterTo);

        this.setState({
            reservationsFiltered: res
        })
    }

    render() {

        const { item, reservationsFiltered } = this.state;

        return item && (
            <div>
                <h2>{item.getName()}</h2>

                <p><strong>Email:</strong> {item.getEmail()}</p>
                <p><strong>Phone:</strong> {item.getPhoneNumber()}</p>
                <p><strong>Address:</strong> {item.getAddress()}</p>

                <p><strong>Reservations:</strong></p>
                <form action="#" className="form-inline" onSubmit={this.filter}>
                    <div className="form-group">

                        <input
                            type="datetime-local"
                            className="form-control"
                            id="filterFrom"
                            placeholder="From"
                            onChange={e => this.setState({ filterFrom: e.target.value })}
                            value={this.state.filterFrom}
                            name="filterFrom" />
                    </div>

                    <div className="form-group">

                        <input
                            type="datetime-local"
                            className="form-control"
                            id="filterTo"
                            placeholder="To"
                            onChange={e => this.setState({ filterTo: e.target.value })}
                            value={this.state.filterTo}
                            name="filterTo" />
                    </div>
                    <button type="submit" className="btn btn-default">Filter</button>
                </form>
                {reservationsFiltered && <ReservationList items={reservationsFiltered} onRemove={this.onRemove}/>}
            </div>
        );
    }
}

function dateToStr(item) {
    return `${item.year}-${item.monthValue}-${item.dayOfMonth}T${item.hour}:${pad2(item.minute)}`;
}

function pad2(number) {
    return (number < 10 ? '0' : '') + number
}

