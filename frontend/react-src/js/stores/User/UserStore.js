import axios from 'axios';
import Bullet from 'bullet-pubsub';
import UserItem from './UserItem';
import UserConstants from './UserConstants';

const UserStore = {

    async getItemById(id) {
        try {
            return await axios('/pa165/rest/users/' + id);
        } catch (e) {
            //
        }
    },

    async getAllItems() {
        try {
            return await axios.get('/pa165/rest/users', { headers: { Authorization: localStorage.getItem("token") } });
        } catch (e) {
            //
        }
    },

    /**
     * Emits the change event listener.
     */
    emitChangeListener() {
        Bullet.trigger(UserConstants.EVENT_CHANGE);
    },

    /**
     * Adds the change event listener.
     *
     * @param {Function} callback
     */
    addChangeListener(callback) {
        Bullet.on(UserConstants.EVENT_CHANGE, callback);
    },

    /**
     * Removes the change event listener.
     *
     * @param {Function} callback
     */
    removeChangeListener(callback) {
        Bullet.off(UserConstants.EVENT_CHANGE, callback);
    },

    dispatchIndex: (payload) => {
        switch (payload.type) {
            case UserConstants.USER_REGISTER:
                // Create room
                axios.post('/api/v1/users', {
                    fullName: payload.data.fullName,
                    email: payload.data.email,
                    phoneNumber: payload.data.phoneNumber,
                    address: payload.data.address,
                    resevations: [],
                    role: "USER",
                    password: payload.data.password
                }).then(function (response) {
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                });
                break;
        }
    },
};

export default UserStore;

