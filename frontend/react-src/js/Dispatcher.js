import RoomStore from './stores/Room/RoomStore';
import UserStore from './stores/User/UserStore'

export default function (payload) {
    RoomStore.dispatchIndex(payload);
    UserStore.dispatchIndex(payload);
};
