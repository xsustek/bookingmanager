import axios from 'axios';
import Bullet from 'bullet-pubsub';
import RoomItem from './RoomItem';
import RoomConstants from './RoomConstants';
import { ApiCallerHelper } from '../../ApiCallerHelper';

const RoomStore = {

    async getItemById(id) {
        try {
            let room = await ApiCallerHelper.callGet('/pa165/rest/rooms/' + id);
            return Object.assign(new RoomItem, room.data);
        } catch (e) {
            console.log(e);
        }
    },

    async getAllItems() {
        try {
            let rooms = await ApiCallerHelper.callGet('/pa165/rest/rooms');
            return ApiCallerHelper.mapTo(new RoomItem, rooms.data._embedded.roomApiDTOList);
        } catch (e) {
            console.log(e);
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
                console.log(payload);
                ApiCallerHelper.callPost('/pa165/rest/rooms/create', payload.data)
                    .then(function (response) {
                        RoomStore.emitChangeListener();
                        console.log('Room created.');
                    }).catch(function (error) {
                    console.log(error);
                });
                break;
        }
    },
};

export default RoomStore;

