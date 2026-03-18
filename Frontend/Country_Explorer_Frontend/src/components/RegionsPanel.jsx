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

      <hr />

      <h3>Countries</h3>

      <div className="liste">
        {countries.map((c) => (
          <button
            key={c.id}
            className="item"
            onClick={() => onPickCountry(c.id)}
          >
            {c.name}
          </button>
        ))}
      </div>
    </div>
  );
}