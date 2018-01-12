import React from "react";
import {Link,} from 'react-router-dom'

const ReservationList = ({items, onRemove}) => (
    <table className="table">
    <thead>
        <tr>
            <th>#</th>
            <th>Room number</th>
            <th>Starts</th>
            <th>Ends</th>
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
                    {item.getRoom().roomNumber}
                </td>
                <td>
                    {`${item.getStartTime().dayOfMonth}. ${item.getStartTime().monthValue}. ${item.getStartTime().year} ${item.getStartTime().hour}:${pad2(item.getStartTime().minute)}`}
                </td>
                <td>
                {`${item.getEndTime().dayOfMonth}. ${item.getEndTime().monthValue}. ${item.getEndTime().year} ${item.getEndTime().hour}:${pad2(item.getEndTime().minute)}`}
                </td>
                <td>
                    <button className="btn btn-danger btn-xs" onClick={() => onRemove(item)}>x</button>
                </td>
            </tr>
            
        ))}
    </tbody>
</table>
);

function pad2(number) {
   
    return (number < 10 ? '0' : '') + number
  
}

export default ReservationList;