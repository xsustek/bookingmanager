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
        try {
            const token = await ApiCallerHelper.callPost("/pa165/rest/users/auth", credentials);
            if (token.data) {
                window.localStorage.setItem("userToken", token.data.token);
                if (db.auth) {
                    return true;
                }
                db.auth = true;
            }
            else {
                window.localStorage.removeItem("userToken");
                db.auth = false;
            }


            AppStore.emitChangeListener();
            return true;
        } catch (e) {
            console.log(e);
        }

        return false;
    },

    signout() {
        db.auth = false;
        window.localStorage.removeItem("userToken");
        AppStore.emitChangeListener();
    },

    async isSignedInAsync() {
        if (!db.auth) {
            db.auth = (await ApiCallerHelper.callPost("/pa165/rest/users/isSignIn", {token: window.localStorage.getItem("userToken")})).data;
        }
        return db.auth;
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