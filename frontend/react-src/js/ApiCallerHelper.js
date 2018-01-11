import axios, {AxiosRequestConfig, AxiosResponse} from 'axios';

export class ApiCallerHelper {

    /**
     * @param {string} url
     * @param {AxiosRequestConfig} config
     * @returns {AxiosResponse<any>}
     */
    static async callGet(url, config) {
        let token = window.localStorage.getItem("userToken");
        if (token && token.length > 0) {
            if (!config) {
                config = {
                    headers: {
                        'Authorization': "Bearer " + token
                    }
                }
            }
            else {
                config.headers = {Authorization: "Bearer " + token};
            }

        }
        return await axios(url, config);
    }

    static async callPost(url, data, config) {
        let token = window.localStorage.getItem("userToken");
        if (token && token.length > 0) {
            if (!config) {
                config = {
                    headers: {
                        'Authorization': "Bearer " + token
                    }
                }
            }
            else {
                config.headers = {Authorization: "Bearer " + token};
            }

        }
        return await axios.post(url, data, config);
    }

    static async callDelete(url, config) {
        let token = window.localStorage.getItem("userToken");
        if (token && token.length > 0) {
            if (!config) {
                config = {
                    headers: {
                        'Authorization': "Bearer " + token
                    }
                }
            }
            else {
                config.headers = {Authorization: "Bearer " + token};
            }

        }
        return await axios.delete(url, config);
    }

    static mapTo(target, collection) {
        return collection.map(d => Object.assign(target, d));
    }
}