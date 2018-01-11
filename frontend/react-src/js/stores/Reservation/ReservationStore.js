import { ApiCallerHelper } from "../../ApiCallerHelper";
import ReservationItem from "./ReservationItem";
import ReservationConstants from "./ReservationConstants";

const ReservationStore = {
    async getItemById(id) {
        try {
            const reservation = ApiCallerHelper.callGet("/pa165/rest/reservations/" + id);
            return Object.assign(new ReservationItem, reservation.data);
        } catch(e) {
            console.log(e);
        }
    },

    async getAllItems() {
        try {
            const reservations = ApiCallerHelper.callGet("/pa165/rest/reservations");
            return ApiCallerHelper.mapTo(new ReservationItem, reservations.data._embedded);
        } catch(e) {
            console.log(e);
        }
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