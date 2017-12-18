import axios from 'axios';
import Bullet from 'bullet-pubsub';
import HotelItem from './HotelItem';
import HotelConstants from './HotelConstants';

const HotelStore = {

    async getItemById(id) {
        try {
            const rooms = await
            axios('/pa165/rest/hotels/room/' + id);
            const result = await
            axios('/pa165/rest/hotels/' + id);
            result.data.rooms = rooms.data._embedded.roomApiDTOList;
            return Object.assign(new HotelItem, result.data);
        } catch (e) {
            console.log('Hotel Not Found');
        }
    },

    async getAllItems() {
        try {
            const result = await
            axios('/pa165/rest/hotels');
            let hotels = await
            result.data._embedded.hotelWithoutRoomsDTOList.map(async
            h =
        >
            {
                let rooms = await
                axios('/pa165/rest/hotels/room/' + h.id);
                if (rooms.data._embedded && rooms.data._embedded.roomApiDTOList) {
                    h.rooms = rooms.data._embedded.roomApiDTOList;
                }
                else {
                    h.rooms = [];
                }
                return h;
            }
        )
            ;
            return _mapToItem(hotels);
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

    dispatchIndex: (payload) => {
        switch (payload.type) {
            case HotelConstants.HOTEL_DELETE:
                axios.delete('http://localhost:8080/pa165/rest/hotels/' + payload.data.id)
                    .then(function (response) {
                        HotelStore.emitChangeListener();
                        console.log('Hotel deleted.');
                    }).catch(function (error) {
                        console.log(error);
                    });
                break;
            case HotelConstants.HOTEL_CREATE:
                axios.post('http://localhost:8080/pa165/rest/hotels/create', payload.data)
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
    return data.map(obj = > {
        return Object.assign(new HotelItem, obj)
    }
)
    ;
}

export default HotelStore;

