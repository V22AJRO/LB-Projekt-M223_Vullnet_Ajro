export default function RegionsPanel({
  activeRegion,
  onChangeRegion,
  countries,
  onPickCountry,
}) {
  return (
    <div className="box">
      <h2>Regions</h2>

      <button className="btn" onClick={() => onChangeRegion("europe")}>
        Europe
      </button>
      <button className="btn" onClick={() => onChangeRegion("asia")}>
        Asia
      </button>
      <button className="btn" onClick={() => onChangeRegion("africa")}>
        Africa
      </button>
      <button className="btn" onClick={() => onChangeRegion("americas")}>
        Americas
      </button>

      <hr />

      <h3>Countries</h3>

      <div className="liste">
        {countries.map((c) => (
          <button
            key={c.code}
            className="item"
            onClick={() => onPickCountry(c.code)}
          >
            {c.name}
          </button>
        ))}
      </div>
    </div>
  );
}
