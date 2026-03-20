import { createContext, useContext, useState, useEffect } from "react";
import AuthService from "../services/auth.service";

/*
Dieser Context stellt Login-Daten und Login-Funktionen
für die ganze React-Anwendung zur Verfügung.
*/
const AuthContext = createContext();

/*
Custom Hook zum einfacheren Zugriff auf den AuthContext.
*/
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

/*
Der Provider legt den globalen Login-Zustand fest
und macht ihn für die ganze Anwendung verfügbar.
*/
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  /*
  Beim Start der App wird geprüft,
  ob bereits ein Benutzer im localStorage gespeichert ist.
  */
  useEffect(() => {
    const currentUser = AuthService.getCurrentUser();
    setUser(currentUser);
    setLoading(false);
  }, []);

  /*
  Führt den Login aus und übernimmt
  den Benutzer danach in den globalen Zustand.
  */
  const login = async (username, password) => {
    setLoading(true);
    try {
      const response = await AuthService.login(username, password);
      setUser(response);
      return response;
    } finally {
      setLoading(false);
    }
  };

  /*
  Meldet den Benutzer ab.
  */
  const logout = () => {
    setUser(null);
    AuthService.logout();
  };

  /*
  Gibt das aktuell gespeicherte JWT zurück.
  */
  const getToken = () => {
    const currentUser = AuthService.getCurrentUser();
    return currentUser ? currentUser.token : null;
  };

  const value = {
    user,
    login,
    logout,
    getToken,
    loading,
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
};