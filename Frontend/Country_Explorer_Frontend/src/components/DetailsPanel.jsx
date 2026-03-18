export default function DetailsPanel({ details }) {
  let name = "";
  let flagUrl = "";
  let capital = "-";
  let region = "-";
  let subregion = "-";
  let population = "-";
  let languages = "-";
  let currencies = "-";

  if (details) {
    name = details.name.common;

    if (details.flags && details.flags.png) {
      flagUrl = details.flags.png;
    }

    if (details.capital && details.capital.length > 0) {
      capital = details.capital[0];
    }

    region = details.region || "-";
    subregion = details.subregion || "-";

    if (details.population) {
      population = details.population.toLocaleString("de-CH");
    }

    if (details.languages) {
      languages = Object.values(details.languages).join(", ");
    }

    if (details.currencies) {
      currencies = Object.values(details.currencies)
        .map((c) => c.name)
        .join(", ");
    }
  }

  return (
    <div className="box">
      <h2>Details</h2>

      {!details ? (
        <div className="hinweis">Please select a country on the left.</div>
      ) : (
        <div className="details">
          <div className="detailsTop">
            {flagUrl !== "" ? (
              <img className="flagge" src={flagUrl} alt="Flag" />
            ) : null}

            <div>
              <div className="detailsTitel">{name}</div>
              <div className="detailsKlein">
                {region} / {subregion}
              </div>
            </div>
          </div>

          <div className="detailsZeile">
            <b>Capital:</b> {capital}
          </div>
          <div className="detailsZeile">
            <b>Population:</b> {population}
          </div>
          <div className="detailsZeile">
            <b>Languages:</b> {languages}
          </div>
          <div className="detailsZeile">
            <b>Currencies:</b> {currencies}
          </div>
        </div>
      )}
    </div>
  );
}
