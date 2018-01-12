import React from "react";
import HotelStore from './../stores/Hotel/HotelStore';
import RoomList from "../components/RoomList";
import {removeRoomItem} from "../stores/Room/RoomActions";
import RoomConstants from "../stores/Room/RoomConstants";
import RoomStore from "../stores/Room/RoomStore";

export default class HotelDetailScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            item: null
        }

        this.loadItem = this.loadItem.bind(this);
        this.handleOnRemove = this.handleOnRemove.bind(this);

        // init
        RoomStore.addChangeListener(this.loadItem);
        this.loadItem();
    }

    componentWillUnmount() {
        RoomStore.removeChangeListener(this.loadItem);
    }

    loadItem() {
        HotelStore.getItemById(this.props.match.params.id).then(item => {
            this.setState({
                item: item
            })
        });
    }

    handleOnRemove(id) {
        removeRoomItem({
            id: id
        });
    }

    render() {

        const { item } = this.state;

        return item && (
            <div>
                <h2>{item.getName()}</h2>

                <p>Address: {item.getAddress()}</p>
                {item && <RoomList items={item.rooms} onRemove={this.handleOnRemove}/>}
            </div>
        );
    }
}