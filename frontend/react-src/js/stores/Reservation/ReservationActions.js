import Dispatcher from "../../Dispatcher";
import ReservationConstants from "./ReservationConstants";

function createReservationItem(data) {
    Dispatcher({
        type: ReservationConstants.RESERVATION_CREATE,
        data: data
    });
}

function deleteReservationItem(data) {
    Dispatcher({
        type: ReservationConstants.RESERVATION_DELETE,
        data: data
    });
}

export {
    createReservationItem,
    deleteReservationItem
};