import React from "react";
import HotelStore from './../stores/Hotel/HotelStore';

export default class HotelDetailScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            item: null
        }

        this.loadItem = this.loadItem.bind(this);

        // init
        HotelStore.addChangeListener(this.loadItem);
        this.loadItem();
    }

    componentWillUnmount() {
        HotelStore.removeChangeListener(this.loadItem);
    }

    loadItem() {
        HotelStore.getItemById(this.props.match.params.id).then(item => {
            this.setState({
                item: item
            })
        });
    }

    render() {

        const { item } = this.state;

        return item && (
            <div>
                <h2>{item.getName()}</h2>

                <p>Address: {item.getAddress()}</p>
                <p>Rooms: {item.getRooms().length}</p>
            </div>
        );
    }
}