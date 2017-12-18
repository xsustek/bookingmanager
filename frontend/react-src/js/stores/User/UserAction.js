import Dispatcher from './../Dispatcher';
import UserConstants from './UserConstants';

/**
 * Register user item for given data.
 *
 * @param {Object} data
 * @param {string} data.fullName
 * @param {string} data.email
 * @param {string} data.phoneNumber
 * @param {string} data.address
 * @param {string} data.role
 * @param {Array}  data.reservations
 * @param {string} data.password
 */
function resgisterUserItem(data) {
    Dispatcher({
        type: UserConstants.USER_REGISTER,
        data: data
    });
}

export {
    resgisterUserItem,
};