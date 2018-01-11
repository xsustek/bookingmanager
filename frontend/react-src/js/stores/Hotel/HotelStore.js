import axios from 'axios';
import Bullet from 'bullet-pubsub';
import HotelItem from './HotelItem';
import HotelConstants from './HotelConstants';
import {ApiCallerHelper} from '../../ApiCallerHelper';

const HotelStore = {

    async getItemById(id) {
        try {
            const result = await ApiCallerHelper.callGet('/pa165/rest/hotels/' + id);
            return Object.assign(new HotelItem, result.data);
        } catch (e) {
            console.log('Hotel Not Found');
        }
    },

    async getAllItems() {
        try {
            const result = await ApiCallerHelper.callGet("/pa165/rest/hotels");
            return _mapToItem(result.data._embedded.hotelWithoutRoomsDTOList);
        } catch (e) {
            console.log(e);
        }
    },

    /**
     * Emits the change event listener.
     */
    emitChangeListener() {
        Bullet.trigger(HotelConstants.EVENT_CHANGE);
    },

    /**
     * Adds the change event listener.
     *
     * @param {Function} callback
     */
    addChangeListener(callback) {
        Bullet.on(HotelConstants.EVENT_CHANGE, callback);
    },

    /**
     * Removes the change event listener.
     *
     * @param {Function} callback
     */
    removeChangeListener(callback) {
        Bullet.off(HotelConstants.EVENT_CHANGE, callback);
    },

    dispatchIndex: (payload) => {
        switch (payload.type) {
            case HotelConstants.HOTEL_DELETE:
                ApiCallerHelper.callDelete('/pa165/rest/hotels/' + payload.data.id)
                    .then(function (response) {
                        HotelStore.emitChangeListener();
                        console.log('Hotel deleted.');
                    }).catch(function (error) {
                        console.log(error);
                    });
                break;
            case HotelConstants.HOTEL_CREATE:

                ApiCallerHelper.callPost('/pa165/rest/hotels/create', payload.data)
                    .then(function (response) {
                        HotelStore.emitChangeListener();
                        console.log('Hotel created.');
                    }).catch(function (error) {
                        console.log(error);
                    });
                break;
        }
    },
};

function _mapToItem(data) {
    return data.map(obj => Object.assign(new HotelItem, obj));
}

export default HotelStore;

