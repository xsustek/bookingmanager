import React from "react";
import HotelStore from "../stores/Hotel/HotelStore";
import { Link } from 'react-router-dom';

class HotelListCustomer extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            active: null,
            items: []
        };


        this.loadItems = this.loadItems.bind(this);

        HotelStore.addChangeListener(this.loadItems);
        this.loadItems();
    }

    loadItems() {
        for (var item of this.props.items) {
            HotelStore.getItemById(item.getId()).then(h => {
                this.setState((prevState) => ({
                    items: [h, ...prevState.items]
                }))
            })
        }
    }

    render() {
        // const {items} = this.props;
        const {items, active} = this.state;

        return (
            <table className="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {items.map((item, i) => {
                    return (
                        <React.Fragment key={item.getId()}>
                            <tr>
                                <td>
                                    {item.getName()}
                                </td>
                                <td>
                                    {item.getAddress()}
                                </td>
                                <td>
                                    <button className="btn btn-primary btn-xs" onClick={() => this.setState({ active: i })}>VIEW</button>
                                </td>
                            </tr>
                            {active == i && ( item.rooms.length ? (
                                <tr>
                                    <td colSpan={3}>
                                        <strong><small>Rooms</small></strong>
                                        <hr/>
                                        <table className="table">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Number</th>
                                                    <th>Price</th>
                                                    <th>Type</th>
                                                    <th>Capacity</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {item.rooms.map((room, j) => (
                                                    <tr key={j}>
                                                        <td>
                                                            {j}
                                                        </td>
                                                        <td>
                                                            {room.roomNumber}
                                                        </td>
                                                        <td>
                                                            {room.price} €
                                                        </td>
                                                        <td>
                                                            {room.type}
                                                        </td>
                                                        <td>
                                                            {room.capacity}
                                                        </td>
                                                        <td>
                                                            <Link className="btn btn-xs btn-success" to={{
                                                                pathname: "/reservations/create",
                                                                state: {
                                                                    room: room
                                                                }
                                                            }} >Reservation</Link>
                                                        </td>
                                                    </tr>
                                                ))}
                                            </tbody>
                                        </table>

                                    </td>
                                </tr>
                            ) : (
                                <tr>
                                    <td colSpan={6}>
                                        <strong>
                                            <small>No available rooms</small>
                                        </strong>
                                    </td>
                                </tr>
                            ))}
                        </React.Fragment>
                    )
                })}
                </tbody>
            </table>
        )
    }
}

export default HotelListCustomer;