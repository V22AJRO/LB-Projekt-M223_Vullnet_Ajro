import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export default function Login() {
  const navigate = useNavigate();
  const { user, login } = useAuth();
  const [entries, setEntries] = useState({ username: "", password: "" });

  useEffect(() => {
    if (user) {
      if (user.roles?.includes("ROLE_ADMIN")) {
        navigate("/admin", { replace: true });
      } else {
        navigate("/", { replace: true });
      }
    }
  }, [user, navigate]);

  function store(event) {
    setEntries({
      ...entries,
      [event.target.name]: event.target.value,
    });
  }

  function handleSubmit(event) {
    event.preventDefault();

    login(entries.username, entries.password)
      .then((response) => {
        if (response?.username) {
          if (response.roles?.includes("ROLE_ADMIN")) {
            navigate("/admin", { replace: true });
          } else {
            navigate("/", { replace: true });
          }
        }
      })
      .catch((error) => {
        if (error.response?.status === 401) {
          alert("Wrong username or password");
        } else {
          alert("Login fehlgeschlagen");
        }
      });
  }

  return (
    <div className="centerBox">
      <div className="box">
        <form onSubmit={handleSubmit}>
          <h2>Login</h2>

          <div className="detailsZeile">
            <label htmlFor="username">Username:</label>
          </div>
          <input
            className="input"
            type="text"
            id="username"
            name="username"
            value={entries.username}
            onChange={store}
          />

          <div className="detailsZeile">
            <label htmlFor="password">Passwort:</label>
          </div>
          <input
            className="input"
            type="password"
            id="password"
            name="password"
            value={entries.password}
            onChange={store}
          />

          <div className="buttonRow">
            <button className="btn" type="submit">
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}