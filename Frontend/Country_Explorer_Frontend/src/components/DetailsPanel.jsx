export default function DetailsPanel({
  details,
  isLoggedIn,
  isAdmin,
  onOpenRequest,
}) {
  let name = "";
  let capital = "-";
  let president = "-";
  let population = "-";

  if (details) {
    name = details.name || "-";
    capital = details.capital || "-";
    president = details.president || "-";

    if (details.population) {
      population = details.population.toLocaleString("de-CH");
    }
  }

  return (
    <div className="box">
      <h2>Alpha-Daten</h2>

      {!details ? (
        <div className="hinweis">Bitte links ein Land auswählen.</div>
      ) : (
        <div className="details">
          <div className="detailsTitel">{name}</div>

          <div className="detailsZeile">
            <b>Capital:</b> {capital}
          </div>
          <div className="detailsZeile">
            <b>Population:</b> {population}
          </div>
          <div className="detailsZeile">
            <b>President:</b> {president}
          </div>

          {!isLoggedIn ? (
            <div className="hinweis">
              Für einen Änderungsantrag bitte zuerst einloggen.
            </div>
          ) : isAdmin ? (
            <div className="hinweis">
              Admin sieht den Verlauf in der Admin-Ansicht.
            </div>
          ) : null}

          <button className="btn" onClick={onOpenRequest}>
            Änderungsantrag
          </button>
        </div>
      )}
    </div>
  );
}