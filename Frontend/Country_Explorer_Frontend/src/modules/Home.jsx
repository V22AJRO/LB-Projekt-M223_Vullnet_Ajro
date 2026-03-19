import { useEffect, useState } from "react";
import { useNavigate, useSearchParams, useLocation } from "react-router-dom";
import {
  getCountriesByRegion,
  getCountryDetailsById,
  createChangeRequest,
  getAllChangeRequests,
  approveChangeRequest,
  rejectChangeRequest,
} from "../restcountries";
import { useAuth } from "../contexts/AuthContext";

import RegionsPanel from "../components/RegionsPanel";
import DetailsPanel from "../components/DetailsPanel";
import RequestForm from "../components/RequestForm";
import AdminPanel from "../components/AdminPanel";

export default function Home() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const location = useLocation();
  const { user } = useAuth();

  const [activeRegion, setActiveRegion] = useState("Europe");
  const [countries, setCountries] = useState([]);
  const [details, setDetails] = useState(null);

  const [showRequestForm, setShowRequestForm] = useState(false);
  const [showAdminView, setShowAdminView] = useState(false);

  const [requestField, setRequestField] = useState("population");
  const [requestValue, setRequestValue] = useState("");

  const [requests, setRequests] = useState([]);

  const isLoggedIn = !!user;
  const isAdmin = user?.roles?.includes("ROLE_ADMIN");

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

  function loadRequests() {
    getAllChangeRequests().then((data) => {
      if (!data) data = [];
      setRequests(data);
    });
  }

  useEffect(() => {
    loadRegion(activeRegion);
  }, [activeRegion]);

  useEffect(() => {
    const isAdminRoute =
      searchParams.get("admin") === "true" || location.pathname === "/admin";

    if (isAdmin && isAdminRoute) {
      setShowAdminView(true);
      loadRequests();
    } else {
      setShowAdminView(false);
    }
  }, [isAdmin, searchParams, location.pathname]);

  function openRequestForm() {
    if (!details) {
      alert("Bitte zuerst ein Land auswählen.");
      return;
    }

    if (!isLoggedIn) {
      alert("Für eine Änderungsanfrage bitte zuerst einloggen.");
      navigate("/login");
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

    createChangeRequest({
      countryId: details.id,
      fieldName: requestField,
      newValue: requestValue,
    }).then((response) => {
      if (response) {
        setRequestField("population");
        setRequestValue("");
        setShowRequestForm(false);
        alert("Änderungsantrag wurde gespeichert.");
      }
    });
  }

  function closeAdminView() {
    setShowAdminView(false);
    navigate("/");
  }

  function decideRequest(id, newStatus) {
    const action =
      newStatus === "GENEHMIGT"
        ? approveChangeRequest(id)
        : rejectChangeRequest(id);

    action.then((response) => {
      if (response) {
        // Antrag nach Entscheidung sofort aus der sichtbaren Liste entfernen
        setRequests((currentRequests) =>
          currentRequests.filter((request) => request.id !== id)
        );

        // Länderliste neu laden, damit genehmigte Änderungen fachlich aktuell bleiben
        loadRegion(activeRegion);

        // Nur bei Genehmigung die aktuell gewählten Details neu laden,
        // damit der neue Wert direkt sichtbar wird
        if (newStatus === "GENEHMIGT" && details?.id) {
          getCountryDetailsById(details.id).then((data) => {
            setDetails(data);
          });
        }
      }
    });
  }

  return (
    <div className="seite">
      <h1 className="titel">Country Explorer</h1>

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