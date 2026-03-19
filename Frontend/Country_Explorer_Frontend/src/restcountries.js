import axios from "axios";
import AuthService from "./services/auth.service";

const BASE_URL = "http://localhost:8080";

function buildAuthHeaders() {
  const jwtHeader = AuthService.getJwtHeader();

  if (!jwtHeader) {
    return {};
  }

  return jwtHeader;
}

function handleUnauthorized(error) {
  if (error?.response?.status === 401) {
    AuthService.logout();
    window.location.href = "/login";
    return true;
  }

  return false;
}

export function getCountriesByRegion(region) {
  return axios
    .get(BASE_URL + "/countries?region=" + encodeURIComponent(region))
    .then((response) => response.data)
    .catch(() => []);
}

export function getCountryDetailsById(id) {
  return axios
    .get(BASE_URL + "/countries/" + id)
    .then((response) => response.data)
    .catch(() => null);
}

export function createChangeRequest(data) {
  return axios
    .post(BASE_URL + "/change-requests", data, {
      headers: {
        "Content-Type": "application/json",
        ...buildAuthHeaders(),
      },
    })
    .then((response) => response.data)
    .catch((error) => {
      if (handleUnauthorized(error)) {
        return null;
      }

      console.log(error);
      return null;
    });
}

export function getAllChangeRequests() {
  return axios
    .get(BASE_URL + "/change-requests", {
      headers: {
        ...buildAuthHeaders(),
      },
    })
    .then((response) => response.data)
    .catch((error) => {
      if (handleUnauthorized(error)) {
        return [];
      }

      console.log(error);
      return [];
    });
}

export function approveChangeRequest(id) {
  return axios
    .put(
      BASE_URL + "/change-requests/" + id + "/approve",
      {},
      {
        headers: {
          ...buildAuthHeaders(),
        },
      }
    )
    .then((response) => response.data)
    .catch((error) => {
      if (handleUnauthorized(error)) {
        return null;
      }

      console.log(error);
      return null;
    });
}

export function rejectChangeRequest(id) {
  return axios
    .put(
      BASE_URL + "/change-requests/" + id + "/reject",
      {},
      {
        headers: {
          ...buildAuthHeaders(),
        },
      }
    )
    .then((response) => response.data)
    .catch((error) => {
      if (handleUnauthorized(error)) {
        return null;
      }

      console.log(error);
      return null;
    });
}