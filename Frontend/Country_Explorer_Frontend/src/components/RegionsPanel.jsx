export default function RegionsPanel({
  activeRegion,
  onChangeRegion,
  countries,
  onPickCountry,
}) {
  return (
    <div className="box">
      <h2>Regions</h2>

      <button className="btn" onClick={() => onChangeRegion("Europe")}>
        Europe
      </button>

      <h3>Countries</h3>

      <div className="liste">
        {countries.map((country) => (
          <button
            key={country.id}
            className="item"
            onClick={() => onPickCountry(country.id)}
          >
            {country.name}
          </button>
        ))}
      </div>
    </div>
  );
}