import React from "react";
import RoomStore from "../stores/Room/RoomStore";
export default class RoomDetailScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            room: null
        };

        this.loadRoom =  this.loadRoom.bind(this);

        RoomStore.addChangeListener(this.loadRoom);

        this.loadRoom();
    }

    loadRoom() {
        RoomStore.getItemById(this.props.match.params.id).then(data => {
            this.setState({
                room: data
            });
        });
    }

    componentWillUnmount() {
        RoomStore.removeChangeListener(this.loadRoom);
    }

    render() {
        const {room} = this.state;

        return room && (
            <div>
                <h2>{room.roomNumber}</h2>
            </div>
        );
    }
}