const BASE_URL = "http://localhost:8080";

function loadJson(url, fallback = []) {
  return fetch(url)
    .then((r) => {
      if (!r.ok) {
        throw new Error("Request failed");
      }

      return r.json();
    })
    .catch(() => fallback);
}

export function getCountriesByRegion(region) {
  const url =
    BASE_URL + "/countries?region=" + encodeURIComponent(region);

  return loadJson(url, []);
}

export function searchCountriesByName(name) {
  const url =
    BASE_URL + "/countries?name=" + encodeURIComponent(name);

  return loadJson(url, []);
}

export function getCountryDetailsById(id) {
  const url = BASE_URL + "/countries/" + id;

  return loadJson(url, null);
}