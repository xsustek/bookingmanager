
class HotelItem {

    getId() {
        return this.id;
    }

    getName() {
        return this.name;
    }

    getAddress() {
        return this.address;
    }

    getRooms() {
        if (!this.rooms) return [];
        return this.rooms;
    }
}

export default HotelItem;