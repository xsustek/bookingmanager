import UserItemPresenter from './UserItemPresenter';

class UserItem {

    /**
     * Returns name of the user
     * 
     * @returns {string}
     */
    getName() {
        return this.fullName;
    }

    /**
     * Returns the user item identifier.
     * 
     * @returns {number}
     */
    getId() {
        return this.id;
    }

    /**
     * Returns mail of the user.
     * 
     * @returns {string}
     */
    getEmail() {
        return this.email;
    }

    /**
     * Returns role of the user.
     * 
     * @returns {string}
     */
    getRole() {
        return this.role;
    }

    /**
     * Returns phone number of the user.
     * 
     * @returns {string}
     */
    getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Returns address of the user.
     * 
     * @returns {string}
     */
    getAddress() {
        return this.address;
    }

    /**
     * Returns reservations of the user.
     * 
     * @returns {Array}
     */
    getReservations() {
        return this.reservations;
    }

    /**
     * Returns presenter for the item.
     *
     * @returns {UserItemPresenter}
     */
    present() {
        return new UserItemPresenter(this);
    }
}

export default UserItem;
