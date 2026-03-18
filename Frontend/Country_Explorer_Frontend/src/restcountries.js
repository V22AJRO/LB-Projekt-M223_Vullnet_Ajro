const BASE_URL = "https://restcountries.com/v3.1";

function loadJson(url) {
  return fetch(url)
    .then((r) => r.json())
    .catch(() => []);
}

export function getCountriesByRegion(region) {
  const url = BASE_URL + "/region/" + region + "?fields=name,cca3,region";
  return loadJson(url);
}

export function searchCountriesByName(name) {
  const url =
    BASE_URL +
    "/name/" +
    encodeURIComponent(name) +
    "?fields=name,cca3,region";
  return loadJson(url);
}

export function getCountryDetailsByAlpha(code) {
  const url =
    BASE_URL +
    "/alpha/" +
    code +
    "?fields=name,flags,capital,region,subregion,population,languages,currencies";
  return loadJson(url);
}
