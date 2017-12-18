import axios from 'axios';
import Bullet from 'bullet-pubsub';
import HotelItem from './HotelItem';
import HotelConstants from './HotelConstants';

const HotelStore = {

    async getItemById(id) {
        try {
            const result = await
            axios('/pa165/rest/hotels/' + id);
            return Object.assign(new HotelItem, result.data);
        } catch (e) {
            console.log('Hotel Not Found');
        }
    },

    async getAllItems() {
        try {
            const result = await
            axios('/pa165/rest/hotels');
            return _mapToItem(result.data._embedded.hotelWithoutRoomsDTOList);
        } catch (e) {
            //
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

// dispatchIndex: (payload) => {
//     switch (payload.type) {
//         case HotelConstants.Hotel_CREATE:
//             // Create Hotel
//             axios.post('/api/v1/hotels', {
//                 price: payload.data.price,
//                 capacity: payload.data.capacity,
//                 type: payload.data.type,
//             }).then(function (response) {
//                 console.log(response);
//             }).catch(function (error) {
//                 console.log(error);
//             });
//             break;
//     }
// },
};

function _mapToItem(data) {
    return data.map(obj => Object.assign(new HotelItem, obj));
}

export default HotelStore;

