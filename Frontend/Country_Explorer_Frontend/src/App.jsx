import { useEffect, useState } from "react";
import "./App.css";
import { getCountriesByRegion, getCountryDetailsById } from "./restcountries";

import RegionsPanel from "./components/RegionsPanel";
import DetailsPanel from "./components/DetailsPanel";
import LoginPanel from "./components/LoginPanel";
import RequestForm from "./components/RequestForm";
import AdminPanel from "./components/AdminPanel";

export default function App() {
  // ----------------------------
  // STATE
  // ----------------------------
  const [activeRegion, setActiveRegion] = useState("Europe");
  const [countries, setCountries] = useState([]);
  const [details, setDetails] = useState(null);

  const [showLogin, setShowLogin] = useState(false);
  const [showRequestForm, setShowRequestForm] = useState(false);
  const [showAdminView, setShowAdminView] = useState(false);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [loginUsername, setLoginUsername] = useState("");
  const [loginPassword, setLoginPassword] = useState("");

  const [requestField, setRequestField] = useState("population");
  const [requestValue, setRequestValue] = useState("");

  const [requests, setRequests] = useState([
    {
      id: 1,
      countryName: "Switzerland",
      field: "population",
      newValue: "9100000",
      status: "OFFEN",
    },
    {
      id: 2,
      countryName: "Germany",
      field: "president",
      newValue: "Neuer Präsident",
      status: "GENEHMIGT",
    },
  ]);

  // ----------------------------
  // DATEN LADEN
  // ----------------------------
  function loadRegion(region) {
    getCountriesByRegion(region).then((data) => {
      if (!data) data = [];

      const list = data.map((country) => {
        return {
          id: country.id,
          name: country.name,
        };
      });

      setCountries(list);
      setDetails(null);
      setShowRequestForm(false);
    });
  }

  function pickCountry(id) {
    getCountryDetailsById(id).then((data) => {
      setDetails(data);
      setShowRequestForm(false);
    });
  }

  useEffect(() => {
    loadRegion(activeRegion);
  }, [activeRegion]);

  // ----------------------------
  // LOGIN
  // ----------------------------
  function openLogin() {
    setShowLogin(true);
    setShowRequestForm(false);
    setShowAdminView(false);
  }

  function closeLogin() {
    setShowLogin(false);
    setLoginUsername("");
    setLoginPassword("");
  }

  function submitLogin() {
    const username = loginUsername.trim().toLowerCase();

    if (username === "") {
      alert("Bitte Benutzername eingeben.");
      return;
    }

    setIsLoggedIn(true);
    setIsAdmin(username === "admin");
    setShowLogin(false);
    setLoginPassword("");
  }

  function logout() {
    setIsLoggedIn(false);
    setIsAdmin(false);
    setShowLogin(false);
    setShowRequestForm(false);
    setShowAdminView(false);
    setLoginUsername("");
    setLoginPassword("");
    setRequestField("population");
    setRequestValue("");
  }

  // ----------------------------
  // ÄNDERUNGSANTRAG
  // ----------------------------
  function openRequestForm() {
    if (!details) {
      alert("Bitte zuerst ein Land auswählen.");
      return;
    }

    if (!isLoggedIn) {
      alert("Für eine Änderungsanfrage bitte zuerst einloggen.");
      setShowLogin(true);
      return;
    }

    if (isAdmin) {
      alert("Admin erstellt keine Änderungsanträge.");
      return;
    }

    setShowRequestForm(true);
    setShowAdminView(false);
  }

  function closeRequestForm() {
    setShowRequestForm(false);
    setRequestField("population");
    setRequestValue("");
  }

  function submitRequest() {
    if (!details) {
      alert("Bitte zuerst ein Land auswählen.");
      return;
    }

    if (requestValue.trim() === "") {
      alert("Bitte neuen Wert eingeben.");
      return;
    }

    const newRequest = {
      id: Date.now(),
      countryName: details.name,
      field: requestField,
      newValue: requestValue,
      status: "OFFEN",
    };

    setRequests((previousRequests) => [newRequest, ...previousRequests]);
    setRequestField("population");
    setRequestValue("");
    setShowRequestForm(false);

    alert("Änderungsantrag wurde vorbereitet.");
  }

  // ----------------------------
  // ADMIN
  // ----------------------------
  function openAdminView() {
    if (!isAdmin) {
      alert("Diese Ansicht ist nur für Admin.");
      return;
    }

    setShowAdminView(true);
    setShowRequestForm(false);
    setShowLogin(false);
  }

  function closeAdminView() {
    setShowAdminView(false);
  }

  function decideRequest(id, newStatus) {
    setRequests((previousRequests) =>
      previousRequests.map((request) =>
        request.id === id ? { ...request, status: newStatus } : request
      )
    );
  }

  // ----------------------------
  // UI
  // ----------------------------
  return (
    <div className="seite">
      <div className="topbar">
        <div className="topbarTitel">Modul 223 Multiuser App</div>

        <div className="topbarButtons">
          {isAdmin && !showAdminView ? (
            <button className="topbarButton" onClick={openAdminView}>
              Admin
            </button>
          ) : null}

          {isLoggedIn ? (
            <button className="topbarButton" onClick={logout}>
              Logout
            </button>
          ) : (
            <button className="topbarButton" onClick={openLogin}>
              Login
            </button>
          )}
        </div>
      </div>

      <h1 className="titel">Country Explorer</h1>

      {showLogin ? (
        <LoginPanel
          loginUsername={loginUsername}
          loginPassword={loginPassword}
          onChangeUsername={setLoginUsername}
          onChangePassword={setLoginPassword}
          onLogin={submitLogin}
          onCancel={closeLogin}
        />
      ) : null}

      {!showLogin ? (
        <div className={showAdminView ? "gridAdmin" : "grid"}>
          <RegionsPanel
            activeRegion={activeRegion}
            onChangeRegion={setActiveRegion}
            countries={countries}
            onPickCountry={pickCountry}
          />

          <DetailsPanel
            details={details}
            isLoggedIn={isLoggedIn}
            isAdmin={isAdmin}
            onOpenRequest={openRequestForm}
          />

          {showAdminView ? (
            <AdminPanel
              requests={requests}
              onApprove={(id) => decideRequest(id, "GENEHMIGT")}
              onReject={(id) => decideRequest(id, "ABGELEHNT")}
              onClose={closeAdminView}
            />
          ) : null}
        </div>
      ) : null}

      {showRequestForm ? (
        <RequestForm
          details={details}
          requestField={requestField}
          requestValue={requestValue}
          onChangeField={setRequestField}
          onChangeValue={setRequestValue}
          onSubmit={submitRequest}
          onCancel={closeRequestForm}
        />
      ) : null}
    </div>
  );
}