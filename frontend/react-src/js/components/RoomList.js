import React from "react";
import {Link,} from 'react-router-dom'

const RoomList = ({items, onRemove}) => (
    <table className="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Room number</th>
            <th>Price</th>
            <th>Type</th>
            <th>Capacity</th>
            <th>Hotel</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        {items.map((item, i) => (
            <tr key={i}>
                <td>
                    {i}
                </td>
                <td>
                    <Link to={`/rooms/${item.id}`}>{item.roomNumber}</Link>
                </td>
                <td>
                    {item.price}
                </td>
                <td>
                    {item.type}
                </td>
                <td>
                    {item.capacity}
                </td>
                <td>
                    {item.hotel.name}
                </td>
                <td>
                    <button className="btn btn-danger btn-xs" onClick={() => onRemove(item.id)}>DELETE</button>
                </td>
            </tr>
        ))}
        </tbody>
    </table>
);

export default RoomList;