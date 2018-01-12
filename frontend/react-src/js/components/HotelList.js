import React from "react";
import {
    Link,
} from 'react-router-dom'

const HotelList = ({ items, onRemove }) => (
    <table className="table">
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Address</th>
                <th>Rooms</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            {items.map((item, i) => {
                return (
                    <tr key={i}>
                        <td>
                            {i}
                        </td>
                        <td>
                            <Link to={`/hotels/${item.getId()}`}>{item.getName()}</Link>
                        </td>
                        <td>
                            {item.getAddress()}
                        </td>
                        <td>
                            {item.getRooms()}
                        </td>
                        <td>
                            <button className="btn btn-danger btn-xs" onClick={() => onRemove(item.getId())}>DELETE</button>
                        </td>
                    </tr>
                )
            })}
        </tbody>
    </table>
);

export default HotelList;