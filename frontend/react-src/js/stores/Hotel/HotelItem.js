import RoomItem from "../Room/RoomItem";
class HotelItem {

    /**
     * @returns {number}
     */
    getId() {
        return this.id;
    }

    /**
     * @returns {string}
     */
    getName() {
        return this.name;
    }

    /**
     * @returns {string}
     */
    getAddress() {
        return this.address;
    }

    /**
     * @returns {RoomItem[]}
     */
    getRooms() {
        if (!this.rooms) return [];
        return this.rooms;
    }
}

export default HotelItem;