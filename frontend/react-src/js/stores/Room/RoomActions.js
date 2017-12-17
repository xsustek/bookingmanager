import Dispatcher from './../Dispatcher';
import RoomConstants from './RoomConstants';

/**
 * Creates room item for given data.
 *
 * @param {Object} data
 * @param {number} data.price
 * @param {string} data.type
 * @param {number} data.capacity
 */
function createRoomItem(data) {
    Dispatcher({
        type: RoomConstants.ROOM_CREATE,
        data: data
    });
}

export {
    createRoomItem,
};