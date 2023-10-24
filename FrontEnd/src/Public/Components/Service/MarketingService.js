import {BASE_URL_SERVER} from "../config/server";
import axios from "axios";

const API_ENDPOINT = {
    // Client
    LIST_MARKETING: "/api/v1/marketing",
    LIST_MARKETING_USER: "/api/v1/marketing/user/",
    LIST_STATUS_MARKETING: "/api/v1/marketing",
    DETAIL_MARKETING: "/api/v1/marketing/",
    CREATE_MARKETING: "/api/v1/marketing",
    UPDATE_MARKETING: "/api/v1/marketing/",
    DELETE_MARKETING: "/api/v1/marketing/",
    // Admin
    ADMIN_LIST_MARKETING: "/admin/api/marketing",
    ADMIN_LIST_STATUS_MARKETING: "/admin/api/marketing/",
    ADMIN_DETAIL_MARKETING: "/admin/api/marketing/",
    ADMIN_CREATE_MARKETING: "/admin/api/marketing",
    ADMIN_UPDATE_MARKETING: "/admin/api/marketing",
    ADMIN_DELETE_MARKETING: "/admin/api/marketing/",
}

class MarketingService {
    //
    listMarketing = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.LIST_MARKETING, config);
    }

    listMarketingByUser = (id, param) => {
        var page = param.page;
        var size = param.size;
        var status = param.status;

        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.LIST_MARKETING_USER + id + `?page=${page}&size=${size}&status=${status}`, config);
    }

    createMarketing = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.CREATE_MARKETING, data, config);
    }

    updateMarketing = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.UPDATE_MARKETING, data, config);
    }

    detailMarketing = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.DETAIL_MARKETING + id, config)
    }

    deleteMarketing = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.delete(BASE_URL_SERVER + API_ENDPOINT.DELETE_MARKETING + id, config)
    }

    // ADMIN
    adminListMarketing = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_MARKETING, config);
    }

    adminListStatusMarketing = (param) => {
        var page = param.page;
        var size = param.size;
        var status = param.status;
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_STATUS_MARKETING + `?page=${page}&size=${size}&status=${status}`, config);
    }

    adminDetailMarketing = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_DETAIL_MARKETING + id, config)
    }

    adminCreateMarketing = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.ADMIN_CREATE_MARKETING, data, config);
    }

    adminUpdateMarketing = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.ADMIN_UPDATE_MARKETING, data, config);
    }

    adminDeleteMarketing = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.delete(BASE_URL_SERVER + API_ENDPOINT.ADMIN_DELETE_MARKETING + id, config)
    }

}

const marketingService = new MarketingService();
export default marketingService;