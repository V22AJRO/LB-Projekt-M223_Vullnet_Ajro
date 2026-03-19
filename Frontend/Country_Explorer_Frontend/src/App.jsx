import { Routes, Route } from "react-router-dom";
import Layout from "./modules/Layout";
import Home from "./modules/Home";
import Login from "./modules/Login";
import PrivateRoute from "./components/PrivateRoute";
import AdminRoute from "./components/AdminRoute";

export default function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="login" element={<Login />} />
          <Route
            path="admin"
            element={
              <PrivateRoute>
                <AdminRoute>
                  <Home />
                </AdminRoute>
              </PrivateRoute>
            }
          />
        </Route>
      </Routes>
    </>
  );
}