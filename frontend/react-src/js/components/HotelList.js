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
            </tr>
        </thead>
        <tbody>
            {items.map((item, i) => (
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
                        {item.getRooms().length}
                    </td>
                    <td>
                        <button onClick={() => onRemove(item.getId())}>DELETE</button>
                    </td>
                </tr>
            ))}
        </tbody>
    </table>
);

export default HotelList;