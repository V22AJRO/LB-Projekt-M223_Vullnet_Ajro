export default function AdminPanel({
  requests,
  onApprove,
  onReject,
  onClose,
}) {
  return (
    <div className="box">
      <h2>Änderungsanträge / Verlauf</h2>

      {requests.length === 0 ? (
        <div className="hinweis">Keine Anträge vorhanden.</div>
      ) : (
        <div className="liste">
          {requests.map((request) => (
            <div key={request.id} className="adminItem">
              <div className="detailsZeile">
                <b>Land:</b> {request.countryName}
              </div>
              <div className="detailsZeile">
                <b>Feld:</b> {request.field}
              </div>
              <div className="detailsZeile">
                <b>Neuer Wert:</b> {request.newValue}
              </div>
              <div className="detailsZeile">
                <b>Status:</b> {request.status}
              </div>

              <div className="buttonRow">
                <button className="btn" onClick={() => onApprove(request.id)}>
                  Genehmigen
                </button>
                <button className="btn" onClick={() => onReject(request.id)}>
                  Ablehnen
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="buttonRow">
        <button className="btn" onClick={onClose}>
          Zurück
        </button>
      </div>
    </div>
  );
}