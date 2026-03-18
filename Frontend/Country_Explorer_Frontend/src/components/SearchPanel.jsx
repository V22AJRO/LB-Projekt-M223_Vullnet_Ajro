export default function SearchPanel({
  searchText,
  onChangeSearchText,
  onSearch,
  results,
  hasSearched,
  onPickCountry,
}) {
  return (
    <div className="box">
      <h2>Search</h2>

      <div className="sucheZeile">
        <input
          className="input"
          value={searchText}
          onChange={(e) => onChangeSearchText(e.target.value)}
          placeholder="Country (example: Switzerland, Germany, Russia)"
        />
        <button className="btn" onClick={onSearch}>
          Search
        </button>
      </div>

      {results.length === 0 ? (
        hasSearched ? <div className="hinweis">No country found</div> : null
      ) : (
        <div className="liste">
          {results.map((c) => (
            <button
              key={c.code}
              className="item"
              onClick={() => onPickCountry(c.code)}
            >
              {c.name}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}
