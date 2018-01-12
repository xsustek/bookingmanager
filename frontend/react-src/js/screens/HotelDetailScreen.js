import React from "react";
import HotelStore from './../stores/Hotel/HotelStore';
import RoomStore from './../stores/Room/RoomStore';
import {createRoomItem} from './../stores/Room/RoomActions';
import RoomList from "../components/RoomList";

export default class HotelDetailScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            item: null,
            errors: null,
            formRoomNumber: '',
            formCapacity: '',
            formType: 'SINGLE',
            formPrice: '',
        };

        this.loadItem = this.loadItem.bind(this);
        this.handleCreate = this.handleCreate.bind(this);

        // init
        HotelStore.addChangeListener(this.loadItem);
        RoomStore.addChangeListener(this.loadItem);
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

    handleCreate(e) {
        e.preventDefault();

        createRoomItem({
            roomNumber: this.state.formRoomNumber,
            price: this.state.formPrice,
            capacity: this.state.formCapacity,
            type: this.state.formType,
            hotel: {
                id: this.state.item.getId()
            }
        })
    }

    render() {

        const { item, errors } = this.state;

        return item && (
            <div>
                <h2>{item.getName()}</h2>

                <p>Address: {item.getAddress()}</p>

                <h4>Create new room</h4>
                <div className="row">
                    <div className="col-md-6">
                        {errors && (
                            <div className="alert alert-dismissible alert-danger">
                                {errors.roomNumber && <p>{errors.roomNumber}</p>}
                                {errors.price && <p>{errors.price}</p>}
                                {errors.type && <p>{errors.type}</p>}
                                {errors.capacity && <p>{errors.capacity}</p>}
                            </div>
                        )}
                        <form className="form-horizontal" onSubmit={this.handleCreate}>
                            <div className={"form-group" + (errors && errors.roomNumber ? ' has-error' : '')}>
                                <label htmlFor="inputRoomNumber" className="col-lg-2 control-label">Number</label>
                                <div className="col-lg-10">
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="inputRoomNumber"
                                        placeholder="Room number"
                                        onChange={e => this.setState({ formRoomNumber: e.target.value })}
                                        value={this.state.formRoomNumber}
                                        name="name" />
                                </div>
                            </div>

                            <div className={"form-group" + (errors && errors.price ? ' has-error' : '')}>
                                <label htmlFor="inputPrice" className="col-lg-2 control-label">Price</label>
                                <div className="col-lg-10">
                                    <input
                                        type="number"
                                        className="form-control"
                                        id="inputPrice"
                                        placeholder="Price"
                                        onChange={e => this.setState({ formPrice: e.target.value })}
                                        value={this.state.formPrice}
                                        name="name" />
                                </div>
                            </div>

                            <div className={"form-group" + (errors && errors.type ? ' has-error' : '')}>
                                <label htmlFor="inputType" className="col-lg-2 control-label">Type</label>
                                <div className="col-lg-10">

                                    <select
                                        onChange={e => this.setState({ formType: e.target.value })}
                                        value={this.state.formType}
                                        className="form-control"
                                        id="inputType"
                                        placeholder="Type"
                                    >
                                        <option value="SINGLE">SINGLE</option>
                                        <option value="DOUBLE">DOUBLE</option>
                                        <option value="TRIPLE">TRIPLE</option>
                                        <option value="QUAD">QUAD</option>
                                        <option value="QUEEN">QUEEN</option>
                                        <option value="QUEEN">QUEEN</option>
                                        <option value="KING">KING</option>
                                        <option value="KING">KING</option>
                                        <option value="TWIN">TWIN</option>
                                        <option value="STUDIO">STUDIO</option>
                                    </select>

                                </div>
                            </div>

                            <div className={"form-group" + (errors && errors.capacity ? ' has-error' : '')}>
                                <label htmlFor="inputCapacity" className="col-lg-2 control-label">Capacity</label>
                                <div className="col-lg-10">
                                    <input
                                        type="number"
                                        className="form-control"
                                        id="inputCapacity"
                                        placeholder="Capacity"
                                        onChange={e => this.setState({ formCapacity: e.target.value })}
                                        value={this.state.formCapacity}
                                        name="name" />
                                </div>
                            </div>

                            <div className="form-group">
                                <div className="col-lg-10 col-lg-offset-2">
                                    <button type="submit" className="btn btn-success">Create</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <hr />

                {item && <RoomList items={item.rooms} onRemove={this.handleOnRemove}/>}
            </div>
        );
    }
}