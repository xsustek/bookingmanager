import { ApiCallerHelper } from "../../ApiCallerHelper";
import Bullet from "bullet-pubsub";
import ReservationItem from "./ReservationItem";
import ReservationConstants from "./ReservationConstants";

const ReservationStore = {
    async getItemById(id) {
        try {
            const reservation = await ApiCallerHelper.callGet("/pa165/rest/reservations/" + id);
            return Object.assign(ReservationItem, reservation.data);
        } catch(e) {
            console.log(e);
        }
    },

    async getAllItems() {
        try {
            const reservations = await ApiCallerHelper.callGet("/pa165/rest/reservations");
            return ApiCallerHelper.mapTo(ReservationItem, reservations.data._embedded.reservationDTOList);
        } catch(e) {
            console.log(e);
        }
    },

    async getUserReservationItem() {
        try {
            const reservations = await ApiCallerHelper.callGet("/pa165/rest/reservations/user");
            return ApiCallerHelper.mapTo(ReservationItem, reservations.data._embedded.reservationDTOList);
        } catch(e) {
            console.log(e);
        }
    },

    /**
     * Emits the change event listener.
     */
    emitChangeListener() {
        Bullet.trigger(ReservationConstants.EVENT_CHANGE);
    },

    /**
     * Adds the change event listener.
     *
     * @param {Function} callback
     */
    addChangeListener(callback) {
        Bullet.on(ReservationConstants.EVENT_CHANGE, callback);
    },

    /**
     * Removes the change event listener.
     *
     * @param {Function} callback
     */
    removeChangeListener(callback) {
        Bullet.off(ReservationConstants.EVENT_CHANGE, callback);
    },

    dispatchIndex: (payload) => {
        switch (payload.type) {
            case ReservationConstants.RESERVATION_DELETE:
                ApiCallerHelper.callDelete('/pa165/rest/reservations/' + payload.data.id)
                    .then(function (response) {
                        ReservationStore.emitChangeListener();
                        console.log('Reservation deleted.');
                    }).catch(function (error) {
                        console.log(error);
                    });
                break;
            case ReservationConstants.RESERVATION_CREATE:
                console.log( payload.data );
                ApiCallerHelper.callPost('/pa165/rest/reservations/create', payload.data)
                    .then(function (response) {
                        ReservationStore.emitChangeListener();
                        console.log('Reservation created.');
                    }).catch(function (error) {
                        console.log(error);
                    });
                break;
        }
    }
};

export default ReservationStore;