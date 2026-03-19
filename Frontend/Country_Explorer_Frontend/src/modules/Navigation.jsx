import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export default function Navigation() {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const isAdmin = user?.roles?.includes("ROLE_ADMIN");

  function handleLogout() {
    logout();
    navigate("/");
    navigate(0);
  }

  return (
    <div className="topbar">
      <div className="topbarTitel">Modul 223 Multiuser App</div>

      <div className="topbarUserInfo">
        {user ? (
          <span>
            Angemeldet als: <b>{user.username}</b>
          </span>
        ) : (
          <span>Nicht angemeldet</span>
        )}
      </div>

      <div className="topbarButtons">
        <Link className="topbarLinkButton" to="/">
          Home
        </Link>

        {isAdmin ? (
          <Link className="topbarLinkButton" to="/admin">
            Änderungsanfragen
          </Link>
        ) : null}

        {!user ? (
          <Link className="topbarLinkButton" to="/login">
            Login
          </Link>
        ) : (
          <button className="topbarButton" onClick={handleLogout}>
            Logout
          </button>
        )}
      </div>
    </div>
  );
}