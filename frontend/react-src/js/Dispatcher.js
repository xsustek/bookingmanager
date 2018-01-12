import RoomStore from './stores/Room/RoomStore';
import UserStore from './stores/User/UserStore';
import HotelStore from './stores/Hotel/HotelStore';
import ReservationStore from './stores/Reservation/ReservationStore';

export default function (payload) {
    RoomStore.dispatchIndex(payload);
    UserStore.dispatchIndex(payload);
    HotelStore.dispatchIndex(payload);
    ReservationStore.dispatchIndex(payload);
};
