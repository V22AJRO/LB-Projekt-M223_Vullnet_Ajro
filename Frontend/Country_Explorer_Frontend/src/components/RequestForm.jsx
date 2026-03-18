export default function RequestForm({
  details,
  requestField,
  requestValue,
  onChangeField,
  onChangeValue,
  onSubmit,
  onCancel,
}) {
  return (
    <div className="centerBox requestBoxSpacing">
      <div className="box">
        <h2>Änderungsantrag</h2>

        <div className="detailsZeile">
          <b>Land:</b> {details ? details.name : "-"}
        </div>

        <div className="detailsZeile">Alpha-Daten</div>
        <select
          className="input"
          value={requestField}
          onChange={(event) => onChangeField(event.target.value)}
        >
          <option value="population">Einwohnerzahl</option>
          <option value="president">Präsident</option>
        </select>

        <div className="detailsZeile">Neuer Wert</div>
        <input
          className="input"
          type="text"
          value={requestValue}
          onChange={(event) => onChangeValue(event.target.value)}
          placeholder="Neuen Wert eingeben"
        />

        <div className="buttonRow">
          <button className="btn" onClick={onSubmit}>
            Antrag senden
          </button>
          <button className="btn" onClick={onCancel}>
            Abbrechen
          </button>
        </div>
      </div>
    </div>
  );
}