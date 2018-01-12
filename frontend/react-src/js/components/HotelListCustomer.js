import React from "react";
import {
    Link,
} from 'react-router-dom'

const HotelList = ({ items }) => (
    <table className="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        {items.map((item, i) => (
            <tr key={i}>
                <td>
                    {item.getName()}
                </td>
                <td>
                    {item.getAddress()}
                </td>
                <td>
                    <button className="btn btn-primary btn-xs">VIEW</button>
                </td>
            </tr>
        ))}
        </tbody>
    </table>
);

export default HotelList;