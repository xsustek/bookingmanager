import axios from 'axios';
import Bullet from 'bullet-pubsub';
import UserItem from './UserItem';
import UserConstants from './UserConstants';

const UserStore = {

    async getItemById(id) {
        try {
            const result = await axios('http://localhost:8080/pa165/rest/users/' + id,
                {
                    headers: {
                        'Authorization': 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJlbEBtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE1MTM2MjgxMTI1ODMsImV4cCI6MTUxMzk4ODExMn0.ks-8T2nutC439GyyPVbd07hZGHJMRc9prOCzGuX4Y9k'
                    }
                });
            return Object.assign(new UserItem, result.data);
        } catch (e) {
            //
        }
    },

    async getAllItems() {
        try {
            const result = await axios.get('http://localhost:8080/pa165/rest/users/', {
                headers: {
                    'Authorization': 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJlbEBtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE1MTM2MjgxMTI1ODMsImV4cCI6MTUxMzk4ODExMn0.ks-8T2nutC439GyyPVbd07hZGHJMRc9prOCzGuX4Y9k'
                }
            });
            return _mapToItem(result.data._embedded.userDTOList);
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
            case UserConstants.USER_REMOVE:
                break;
            case UserConstants.USER_REGISTER:
                // Create room
                axios.post('http://localhost:8080/pa165/rest/users/register', {
                    headers: {
                        'Authorization': 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXJlbEBtYWlsLmNvbSIsInJvbGUiOiJBRE1JTiIsImNyZWF0ZWQiOjE1MTM2MjgxMTI1ODMsImV4cCI6MTUxMzk4ODExMn0.ks-8T2nutC439GyyPVbd07hZGHJMRc9prOCzGuX4Y9k'
                    },
                    fullName: payload.data.fullName,
                    email: payload.data.email,
                    phoneNumber: payload.data.phoneNumber,
                    address: payload.data.address,
                    resevations: [],
                    role: payload.data.role,
                    password: payload.data.password
                }).then(function (response) {
                    UserStore.emitChangeListener();
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                });
                break;
        }
    },
};


function _mapToItem(data) {
    return data.map(obj => Object.assign(new UserItem, obj));
}

export default UserStore;

