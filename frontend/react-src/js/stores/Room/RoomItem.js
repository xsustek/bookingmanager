import RoomItemPresenter from './RoomItemPresenter';

class RoomItem {

    /**
     * Returns capacity of the room.
     * 
     * @returns {number}
     */
    getCapacity() {
        return this.capacity;
    }

    /**
     * Returns number of the room.
     *
     * @returns {string}
     */
    getRoomNumber() {
        return this.roomNumber;
    }

    /**
     * Returns the room item identifier.
     * 
     * @returns {number}
     */
    getId() {
        return this.id;
    }

    /**
     * Returns price of the room.
     * 
     * @returns {number}
     */
    getPrice() {
        return this.price;
    }

    /**
     * Returns type of the room.
     * 
     * @returns {string}
     */
    getType() {
        return this.type;
    }

    /**
     * Returns presenter for the item.
     *
     * @returns {RoomItemPresenter}
     */
    present() {
        return new RoomItemPresenter(this);
    }
}

export default RoomItem;
