import Bullet from 'bullet-pubsub';

const db = {
    auth: false,
};

const APP_EVENT_CHANGE = 'APP_EVENT_CHANGE';

const AppStore = {

    signin(credentials) {

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