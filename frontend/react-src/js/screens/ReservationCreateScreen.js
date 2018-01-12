import React from "react";
import {Redirect} from "react-router-dom";
import { createReservationItem } from './../stores/Reservation/ReservationActions';
import AppStore from "../stores/AppStore";

export default class ReservationCreateScreen extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            errors: null,
            formFrom: '',
            formTo: ''
        };

        this.handleCreate = this.handleCreate.bind(this);

        // this.loadItem = this.loadItem.bind(this);
        //
        // // init
        // UserStore.addChangeListener(this.loadItem);
        // this.loadItem();
    }

    // componentWillUnmount() {
    //     UserStore.removeChangeListener(this.loadItem);
    // }

    // loadItem() {
    //     UserStore.getItemById(this.props.match.params.id).then(item => {
    //         this.setState({
    //             item: item
    //         })
    //     });
    // }

    handleCreate(e) {
        e.preventDefault();

        if (!this.validateForm()) {
            return;
        }

        createReservationItem({
            'startTime': this.state.formFrom,
            'endTime': this.state.formTo,
            'userId': AppStore.getAuthUser().getId(),
            'roomId': this.props.location.state.room.id,
            // 'hotel': this.props.location.state.room.hotel
        });

        this.setState({
            formFrom: '',
            formTo: '',
            errors: null
        })
    }

    validateForm() {
        const { formFrom, formTo } = this.state;

        if (formFrom.trim().length < 3) {
            this.setState({
                errors: {
                    from: 'The from must be at least 3 characters.'
                }
            })
            return false;
        }

        if (formTo.trim().length < 3) {
            this.setState({
                errors: {
                    to: 'The to must be at least 3 characters.'
                }
            })
            return false;
        }

        return true;
    }

    render() {
        let room = null;
        const {errors, formFrom, formTo} = this.state;
        const { state } = this.props.location;

        if (state) {
            room = state.room
        }

        if(!room) {
            return <Redirect to="/"/>
        }

        return (
           <div>
               <h1>Create Reservation</h1>
               <h2>{room.hotel.name} > {room.roomNumber}</h2>

               {errors && (
                   <div className="alert alert-dismissible alert-danger">
                       {errors.from && <p>{errors.from}</p>}
                       {errors.to && <p>{errors.to}</p>}
                   </div>
               )}

               <form className="form-horizontal" onSubmit={this.handleCreate}>
                   <div className={"form-group" + (errors && errors.from ? ' has-error' : '')}>
                       <label htmlFor="inputFrom" className="col-lg-2 control-label">From</label>
                       <div className="col-lg-10">
                           <input
                               type="text"
                               className="form-control"
                               id="inputFrom"
                               placeholder="From"
                               onChange={e => this.setState({ formFrom: e.target.value })}
                               value={this.state.formFrom}
                               name="name" />
                       </div>
                   </div>

                   <div className={"form-group" + (errors && errors.to ? ' has-error' : '')}>
                       <label htmlFor="inputTo" className="col-lg-2 control-label">To</label>
                       <div className="col-lg-10">
                           <input name="to"
                                  onChange={e => this.setState({ formTo: e.target.value })}
                                  value={this.state.formTo}
                                  type="text"
                                  className="form-control"
                                  id="inputTo"
                                  placeholder="To" />
                       </div>
                   </div>

                   <div className="form-group">
                       <div className="col-lg-10 col-lg-offset-2">
                           <button type="submit" className="btn btn-success">Create</button>
                       </div>
                   </div>
               </form>
           </div>
        );
    }
}