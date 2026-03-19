import { createContext, useContext, useState, useEffect } from "react";
import AuthService from "../services/auth.service";

/* implements React's Context API for Login fun in entire application. */
const AuthContext = createContext();

/* 
Custom hook to access the authentication context. 
Creates a context that will hold authentication state and methods
*/
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const currentUser = AuthService.getCurrentUser();
    setUser(currentUser);
    setLoading(false);
  }, []);

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

  const register = async (username, email, password, roles = null) => {
    setLoading(true);
    try {
      return await AuthService.register(username, email, password, roles);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    AuthService.logout();
  };

  const getToken = () => {
    const currentUser = AuthService.getCurrentUser();
    return currentUser ? currentUser.token : null;
  };

  const value = {
    user,
    login,
    register,
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