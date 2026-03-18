import { useEffect, useState } from "react";
import {
  getCountriesByRegion,
  searchCountriesByName,
  getCountryDetailsById,
} from "./restcountries";

import RegionsPanel from "./components/RegionsPanel";
import SearchPanel from "./components/SearchPanel";
import DetailsPanel from "./components/DetailsPanel";

export default function App() {
  // ----------------------------
  // STATE (was die App "merkt")
  // ----------------------------
  const [activeRegion, setActiveRegion] = useState("Europe");
  const [countries, setCountries] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [results, setResults] = useState([]);
  const [details, setDetails] = useState(null);
  const [hasSearched, setHasSearched] = useState(false);

  // ----------------------------
  // REGION LADEN
  // ----------------------------
  function loadRegion(region) {
    getCountriesByRegion(region).then((data) => {
      if (!data) data = [];

      const list = data.map((x) => {
        return { id: x.id, name: x.name, code: x.code };
      });

      setCountries(list);

      // sauber halten:
      setResults([]);
      setDetails(null);
      setHasSearched(false);
    });
  }

  // ----------------------------
  // SUCHE STARTEN
  // ----------------------------
  function startSearch() {
    setHasSearched(true);

    const text = searchText.trim();
    if (text === "") {
      setResults([]);
      return;
    }

    searchCountriesByName(text).then((data) => {
      if (!data) data = [];

      const list = data.map((x) => {
        return { id: x.id, name: x.name, code: x.code };
      });

      setResults(list);
    });
  }

  // ----------------------------
  // LAND AUSWÄHLEN (Details laden)
  // ----------------------------
  function pickCountry(id) {
    getCountryDetailsById(id).then((data) => {
      setDetails(data);
    });
  }

  // ----------------------------
  // beim Start und bei Region-Wechsel laden
  // ----------------------------
  useEffect(() => {
    loadRegion(activeRegion);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeRegion]);

  // ----------------------------
  // UI
  // ----------------------------
  return (
    <div className="seite">
      <h1 className="titel">Country Explorer</h1>

      <div className="grid">
        <RegionsPanel
          activeRegion={activeRegion}
          onChangeRegion={setActiveRegion}
          countries={countries}
          onPickCountry={pickCountry}
        />

        <SearchPanel
          searchText={searchText}
          onChangeSearchText={setSearchText}
          onSearch={startSearch}
          results={results}
          hasSearched={hasSearched}
          onPickCountry={pickCountry}
        />

        <DetailsPanel details={details} />
      </div>
    </div>
  );
}