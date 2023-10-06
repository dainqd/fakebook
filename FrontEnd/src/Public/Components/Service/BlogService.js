import { BASE_URL_SERVER } from "../config/server";
import axios from "axios";

const API_ENDPOINT = {
    //
    LIST_BLOG: "/admin/api/blog",
    LIST_STATUS_BLOG: "/admin/api/blog/",
    DETAIL_BLOG: "/admin/api/blog/",
    CREATE_BLOG: "/admin/api/blog",
    UPDATE_BLOG: "/admin/api/blog",
    DELETE_BLOG: "/admin/api/blog/",
    //
    ADMIN_LIST_BLOG: "/admin/api/blog",
    ADMIN_LIST_STATUS_BLOG: "/admin/api/blog/",
    ADMIN_DETAIL_BLOG: "/admin/api/blog/",
    ADMIN_CREATE_BLOG: "/admin/api/blog",
    ADMIN_UPDATE_BLOG: "/admin/api/blog",
    ADMIN_DELETE_BLOG: "/admin/api/blog/",
}
class BlogService {

    //
    listBlog = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.LIST_BLOG, config);
    }

    updateBlog = (id, data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.UPDATE_BLOG + id, data, config);
    }

    detailBlog = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.DETAIL_BLOG + id, config)
    }
    // ADMIN
    adminListBlog = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_BLOG, config);
    }

    adminListStatusBlog = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_STATUS_BLOG + data, config);
    }

    adminDetailBlog = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_DETAIL_BLOG + id, config)
    }

    adminCreateBlog = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.ADMIN_CREATE_BLOG ,data, config);
    }

    adminUpdateBlog = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.ADMIN_UPDATE_BLOG, data, config);
    }

    adminDeleteBlog = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.delete(BASE_URL_SERVER + API_ENDPOINT.ADMIN_DELETE_BLOG + id, config)
    }

}
const blogService = new BlogService();
export default blogService;