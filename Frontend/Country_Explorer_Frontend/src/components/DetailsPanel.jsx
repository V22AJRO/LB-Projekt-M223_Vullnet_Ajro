export default function DetailsPanel({ details }) {
  let name = "";
  let capital = "-";
  let president = "-";
  let population = "-";
  let region = "-";
  let languages = "-";

  if (details) {
    name = details.name || "-";
    capital = details.capital || "-";
    president = details.president || "-";

    if (details.population) {
      population = details.population.toLocaleString("de-CH");
    }

    if (details.region && details.region.name) {
      region = details.region.name;
    }

    if (details.languages && details.languages.length > 0) {
      languages = details.languages.map((language) => language.name).join(", ");
    }
  }

  return (
    <div className="box">
      <h2>Details</h2>

      {!details ? (
        <div className="hinweis">Please select a country on the left.</div>
      ) : (
        <div className="details">
          <div className="detailsTitel">{name}</div>

          <div className="detailsZeile">
            <b>Region:</b> {region}
          </div>
          <div className="detailsZeile">
            <b>Capital:</b> {capital}
          </div>
          <div className="detailsZeile">
            <b>Population:</b> {population}
          </div>
          <div className="detailsZeile">
            <b>President:</b> {president}
          </div>
          <div className="detailsZeile">
            <b>Languages:</b> {languages}
          </div>
        </div>
      )}
    </div>
  );
}