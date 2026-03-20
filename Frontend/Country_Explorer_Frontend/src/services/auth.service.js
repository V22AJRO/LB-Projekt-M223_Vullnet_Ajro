import axios from "axios";

/*
Diese Datei kapselt alle Authentifizierungs-Aufrufe zum Backend.
Hier werden Login, Logout und JWT-Header behandelt.
*/
const API_URL = "http://localhost:8080/api/auth/";

/*
Führt den Login aus.
Bei erfolgreichem Login wird der Benutzer inkl. JWT im localStorage gespeichert.
*/
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

/*
Entfernt den Benutzer aus dem localStorage.
*/
const logout = () => {
  localStorage.removeItem("user");
};

/*
Liest den aktuell gespeicherten Benutzer aus dem localStorage.
*/
const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

/*
Baut den Authorization-Header mit Bearer Token.
Dieser Header wird für geschützte Requests benötigt.
*/
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
  logout,
  getJwtHeader,
  getCurrentUser,
};

export default AuthService;