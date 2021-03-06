import React from "react";
import {
    Link,
} from 'react-router-dom'

const UserList = ({ items, onRemove }) => (
    <table className="table">
        <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
            </tr>
        </thead>
        <tbody>
            {items.map((item, i) => (
                <tr key={i}>
                    <td>
                        {i}
                    </td>
                    <td>
                        <Link to={`/users/${item.getId()}`}>{item.getName()}</Link>
                    </td>
                    <td>
                        {item.getEmail()}
                    </td>
                    <td>
                        {item.getPhoneNumber()}
                    </td>
                    <td>
                        {item.getAddress()}
                    </td>

                </tr>
            ))}
        </tbody>
    </table>
);

export default UserList;