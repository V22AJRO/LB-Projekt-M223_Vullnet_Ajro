import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";   // <-- GLOBAL CSS (Background!)
import "./App.css";     // <-- App Styles

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
