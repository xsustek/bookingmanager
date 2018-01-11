import axios from 'axios';
import Bullet from 'bullet-pubsub';
import RoomItem from './RoomItem';
import RoomConstants from './RoomConstants';
import { ApiCallerHelper } from '../../ApiCallerHelper';

const RoomStore = {

    async getItemById(id) {
        try {
            return await ApiCallerHelper.callGet('/api/v1/rooms/' + id);
        } catch (e) {
            //
        }
    },

    async getAllItems() {
        try {
            return await ApiCallerHelper.callGet('/api/v1/rooms');
        } catch (e) {
            //
        }
    },

    /**
     * Emits the change event listener.
     */
    emitChangeListener() {
        Bullet.trigger(RoomConstants.EVENT_CHANGE);
    },

    /**
     * Adds the change event listener.
     *
     * @param {Function} callback
     */
    addChangeListener(callback) {
        Bullet.on(RoomConstants.EVENT_CHANGE, callback);
    },

    /**
     * Removes the change event listener.
     *
     * @param {Function} callback
     */
    removeChangeListener(callback) {
        Bullet.off(RoomConstants.EVENT_CHANGE, callback);
    },

    dispatchIndex: (payload) => {
        switch (payload.type) {
            case RoomConstants.ROOM_CREATE:
                // Create room
                axios.post('/api/v1/rooms', {
                    price: payload.data.price,
                    capacity: payload.data.capacity,
                    type: payload.data.type,
                }).then(function (response) {
                    console.log(response);
                }).catch(function (error) {
                    console.log(error);
                });
                break;
        }
    },
};

export default RoomStore;

