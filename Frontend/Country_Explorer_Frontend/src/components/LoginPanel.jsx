export default function LoginPanel({
  loginUsername,
  loginPassword,
  onChangeUsername,
  onChangePassword,
  onLogin,
  onCancel,
}) {
  return (
    <div className="centerBox">
      <div className="box">
        <h2>Login</h2>

        <div className="detailsZeile">Benutzername</div>
        <input
          className="input"
          type="text"
          value={loginUsername}
          onChange={(event) => onChangeUsername(event.target.value)}
          placeholder="z. B. user oder admin"
        />

        <div className="detailsZeile">Passwort</div>
        <input
          className="input"
          type="password"
          value={loginPassword}
          onChange={(event) => onChangePassword(event.target.value)}
          placeholder="Passwort"
        />

        <div className="hinweis">
          Benutzername <b>admin</b> = Admin
          <br />
          jeder andere Name = User
        </div>

        <div className="buttonRow">
          <button className="btn" onClick={onLogin}>
            Anmelden
          </button>
          <button className="btn" onClick={onCancel}>
            Zurück
          </button>
        </div>
      </div>
    </div>
  );
}