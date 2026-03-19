import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

const login = (username, password) => {
  return axios
    .post(API_URL + "login", { username, password })
    .then((response) => {
      if (response.data.username) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    })
    .catch((error) => {
      console.log(error);
      throw error;
    });
};

const register = (username, email, password, roles = null) => {
  return axios
    .post(API_URL + "signup", { username, email, password, roles })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.log(error);
      throw error;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const getJwtHeader = () => {
  const user = JSON.parse(localStorage.getItem("user"));

  if (!user || !user.token) {
    return {};
  }

  return {
    Authorization: `Bearer ${user.token}`,
  };
};

const AuthService = {
  login,
  register,
  logout,
  getJwtHeader,
  getCurrentUser,
};

export default AuthService;