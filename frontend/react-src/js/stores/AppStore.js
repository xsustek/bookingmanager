import Bullet from 'bullet-pubsub';
import {ApiCallerHelper} from '../ApiCallerHelper';

const db = {
    auth: false,
};

const APP_EVENT_CHANGE = 'APP_EVENT_CHANGE';

const AppStore = {

    async signin(credentials) {

        console.log(credentials);

        // todo - login
        const token = await ApiCallerHelper.callPost("http://localhost:8080/pa165/rest/users/auth", credentials);

        window.localStorage.setItem("userToken", token.data.token);

        console.log(credentials);

        // todo - login
        if (credentials.email != 'admin@gmail.com' || credentials.password != '000000') {
            return false;
        }
        if (db.auth) {
            return true;
        }
        db.auth = true;
        AppStore.emitChangeListener();
        return true;
    },

    signout() {
        db.auth = false;
        window.localStorage.removeItem("userToken");
        AppStore.emitChangeListener();
    },

    isSignedIn() {
        return db.auth;
    },

    /**
     * Emits the change event listener.
     */
    emitChangeListener() {
        Bullet.trigger(APP_EVENT_CHANGE);
    },

    /**
     * Adds the change event listener.
     *
     * @param {Function} callback
     */
    addChangeListener(callback) {
        Bullet.on(APP_EVENT_CHANGE, callback);
    },

    /**
     * Removes the change event listener.
     *
     * @param {Function} callback
     */
    removeChangeListener(callback) {
        Bullet.off(APP_EVENT_CHANGE, callback);
    },
};

export default AppStore;