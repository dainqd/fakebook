import {BASE_URL_SERVER} from "../config/server";
import axios from "axios";

const API_ENDPOINT = {
    //
    LIST_BLOG: "/api/v1/blogs",
    LIST_BLOG_USER: "/api/v1/blogs/user/",
    LIST_STATUS_BLOG: "/api/v1/blogs",
    DETAIL_BLOG: "/api/v1/blogs/",
    CREATE_BLOG: "/api/v1/blogs",
    UPDATE_BLOG: "/api/v1/blogs/",
    DELETE_BLOG: "/api/v1/blogs/",
    //
    ADMIN_LIST_BLOG: "/admin/api/blogs",
    ADMIN_LIST_STATUS_BLOG: "/admin/api/blogs/",
    ADMIN_DETAIL_BLOG: "/admin/api/blogs/",
    ADMIN_CREATE_BLOG: "/admin/api/blogs",
    ADMIN_UPDATE_BLOG: "/admin/api/blogs",
    ADMIN_DELETE_BLOG: "/admin/api/blogs/",
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

    listBlogByUser = (id, param) => {
        var page = param.page;
        var size = param.size;
        var status = param.status;

        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.LIST_BLOG_USER + id+ `?page=${page}&size=${size}&status=${status}`, config);
    }

    createBlog = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.CREATE_BLOG, data, config);
    }

    updateBlog = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.UPDATE_BLOG, data, config);
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

    deleteBlog = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.delete(BASE_URL_SERVER + API_ENDPOINT.DETAIL_BLOG + id, config)
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

    adminListStatusBlog = (param) => {
        var page = param.page;
        var size = param.size;
        var status = param.status;
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_STATUS_BLOG + `?page=${page}&size=${size}&status=${status}`, config);
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
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.ADMIN_CREATE_BLOG, data, config);
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