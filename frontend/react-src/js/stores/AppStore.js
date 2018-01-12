import Cookies from 'js-cookie';
import jwtDecode from 'jwt-decode';
import Bullet from 'bullet-pubsub';
import {ApiCallerHelper} from '../ApiCallerHelper';
import UserItem from "./User/UserItem";

const db = {
    auth: false,
};

const APP_EVENT_CHANGE = 'APP_EVENT_CHANGE';

const AppStore = {

    async signin(credentials) {

        try {
            const { data } = await ApiCallerHelper.callPost("/pa165/rest/users/auth", credentials);
            const { token } = data;
            if (token) {
                Cookies.set('auth_token', token);
                AppStore.emitChangeListener();
                return true;
            }
        } catch (e) {
            console.log(e);
        }

        AppStore.signout();
        return false;
    },

    signout() {
        Cookies.remove('auth_token');
        AppStore.emitChangeListener();
    },

    // async isSignedInAsync() {
    //     if (!db.auth) {
    //         db.auth = (await ApiCallerHelper.callPost("/pa165/rest/users/isSignIn", {token: window.localStorage.getItem("userToken")})).data;
    //     }
    //     return db.auth;
    // },

    isSignedIn() {
        return !!AppStore.getToken();
    },

    getToken() {
        return Cookies.get('auth_token');
    },

    getAuthUser() {
        if(!AppStore.isSignedIn()) {
            return null;
        }
        const user = Object.assign(new UserItem, jwtDecode(AppStore.getToken()));
        return user;
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