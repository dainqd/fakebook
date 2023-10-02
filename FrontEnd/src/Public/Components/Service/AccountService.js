import { BASE_URL_SERVER } from "../config/server";
import axios from "axios";

const API_ENDPOINT = {
    LIST_ACCOUNT: "/api/v1/user",
    DETAIL_ACCOUNT: "/api/v1/user/",
}
class AccountService {
    listAccount = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.LIST_ACCOUNT, config);
    }

    detailAccount = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.DETAIL_ACCOUNT + id, config)
    }
}
const accountService = new AccountService();
export default accountService;