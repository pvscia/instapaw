import axios from "axios";
import { redirectToLogin } from "../utils/navigator";

var API_URL = "http://localhost:8080/instapaws/api";
const axiosApi = axios.create({
  baseURL: API_URL,
});

axiosApi.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.removeItem("token");
      redirectToLogin();
      return Promise.resolve();
    }
    return Promise.reject(err);
  }
);


axiosApi.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});


export default axiosApi;