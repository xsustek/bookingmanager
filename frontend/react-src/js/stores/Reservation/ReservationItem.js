import RoomItem from "../Room/RoomItem";
import UserItem from "../User/UserItem";
class ReservationItem {

    /**
     * @returns {number}
     */
    getId() {
        return this.id;
    }

    /**
     * @returns {UserItem}
     */
    getUser() {
        return this.user;
    }

    /**
     * @returns {RoomItem}
     */
    getRoom() {
        return this.room;
    }

    /**
     * @returns {Date}
     */
    getStartTime() {
        return this.startTime;
    }

    /**
     * @returns {Date}
     */
    getEndTime() {
        return this.endTime;
    }
}

export default ReservationItem;