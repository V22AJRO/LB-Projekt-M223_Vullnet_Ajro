const BASE_URL = "http://localhost:8080";

function loadJson(url, fallback = []) {
  return fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Request failed");
      }

      return response.json();
    })
    .catch(() => fallback);
}

export function getCountriesByRegion(region) {
  const url = BASE_URL + "/countries?region=" + encodeURIComponent(region);
  return loadJson(url, []);
}

export function getCountryDetailsById(id) {
  const url = BASE_URL + "/countries/" + id;
  return loadJson(url, null);
}