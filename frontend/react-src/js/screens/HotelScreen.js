import React from "react";
import HotelList from './../components/HotelList';
import HotelStore from './../stores/Hotel/HotelStore';
import { removeHotelItem } from './../stores/Hotel/HotelAction';

export default class HotelScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            items: null
        }

        this.loadItems = this.loadItems.bind(this);
        this.handleOnRemove = this.handleOnRemove.bind(this);

        // init
        HotelStore.addChangeListener(this.loadItems);
        this.loadItems();
    }

    componentWillUnmount() {
        HotelStore.removeChangeListener(this.loadItems);
    }

    loadItems() {
        HotelStore.getAllItems().then(items => {
            this.setState({
                items: items
            })
        });
    }

    handleOnRemove(id) {
        removeHotelItem({
            id: id
        });
    }

    render() {

        const { items } = this.state;

        return (
            <div>
                <h2>Hotels</h2>
                {items && <HotelList items={items} onRemove={this.handleOnRemove} />}
            </div>
        );
    }
}