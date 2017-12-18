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

/**
 * Removes hotel item.
 *
 * @param {Object} data
 * @param {number} data.name - Hotel's name.
 * @param {string} data.address - Hotel's address.
 */
function createHotelItem(data) {
    Dispatcher({
        type: HotelConstants.HOTEL_CREATE,
        data: data
    });
}

export {
    removeHotelItem,
    createHotelItem
};