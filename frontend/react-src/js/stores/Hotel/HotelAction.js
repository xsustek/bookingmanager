import Dispatcher from './../../Dispatcher';
import HotelConstants from './HotelConstants';

/**
 * Removes hotel item.
 *
 * @param {Object} data
 * @param {number} data.id - Hotel's ID
 */
function removeHotelItem(data) {
    Dispatcher({
        type: HotelConstants.HOTEL_DELETE,
        data: data
    });
}

export {
    removeHotelItem,
};