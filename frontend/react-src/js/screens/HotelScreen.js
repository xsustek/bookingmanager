import React from "react";
import HotelList from './../components/HotelList';
import HotelStore from './../stores/Hotel/HotelStore';
import AppStore from './../stores/AppStore';
import { removeHotelItem, createHotelItem } from './../stores/Hotel/HotelAction';

export default class HotelScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            items: null,
            formName: '',
            formAddress: '',
            errors: null,
        }

        this.loadItems = this.loadItems.bind(this);
        this.handleOnRemove = this.handleOnRemove.bind(this);
        this.handleCreate = this.handleCreate.bind(this);

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

    handleCreate(e) {
        e.preventDefault();

        if (!this.validateForm()) {
            return;
        }

        createHotelItem({
            'name': this.state.formName,
            'address': this.state.formAddress,
            'rooms': []
        });

        this.setState({
            formName: '',
            formAddress: '',
            errors: null
        })
    }

    validateForm() {
        const { formName, formAddress } = this.state;

        if (formName.trim().length <= 3) {
            this.setState({
                errors: {
                    name: 'The name must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formAddress.trim().length <= 3) {
            this.setState({
                errors: {
                    address: 'The address must be at least 3 characters.'
                }
            })
            return false;
        }

        return true;
    }

    render() {

        const { items, errors, isSignedIn } = this.state;

        return (
            <div>
                <h2>Hotels</h2>

                <h4>Create new one</h4>
                <div className="row">
                    <div className="col-md-6">
                        {errors && (
                            <div className="alert alert-dismissible alert-danger">
                                {errors.name && <p>{errors.name}</p>}
                                {errors.address && <p>{errors.address}</p>}
                            </div>
                        )}
                        <form className="form-horizontal" onSubmit={this.handleCreate}>
                            <div className={"form-group" + (errors && errors.name ? ' has-error' : '')}>
                                <label htmlFor="inputName" className="col-lg-2 control-label">Name</label>
                                <div className="col-lg-10">
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="inputName"
                                        placeholder="Name"
                                        onChange={e => this.setState({ formName: e.target.value })}
                                        value={this.state.formName}
                                        name="name" />
                                </div>
                            </div>

                            <div className={"form-group" + (errors && errors.address ? ' has-error' : '')}>
                                <label htmlFor="inputAddress" className="col-lg-2 control-label">Address</label>
                                <div className="col-lg-10">
                                    <input name="address"
                                        onChange={e => this.setState({ formAddress: e.target.value })}
                                        value={this.state.formAddress}
                                        type="text"
                                        className="form-control"
                                        id="inputAddress"
                                        placeholder="Address" />
                                </div>
                            </div>

                            <div className="form-group">
                                <div className="col-lg-10 col-lg-offset-2">
                                    <button type="submit" className="btn btn-success">Create</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <hr />
                </div>

                {items && <HotelList items={items} onRemove={this.handleOnRemove} />}

            </div>
        );
    }
}