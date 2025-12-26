import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import Login from "./pages/Login";
import Home from "./pages/Home";
import ProtectedRoute from "./middleware/ProtectedRoute";
import { AuthProvider } from "./auth/AuthContext";
import { useEffect } from "react";
import { setNavigate } from "./utils/navigator";

export default function App() {
  
  function NavigationHandler() {
    const navigate = useNavigate();

    useEffect(() => {
      setNavigate(navigate);
    }, [navigate]);

    return null;
  }

  return (
    <AuthProvider>
      <BrowserRouter>
        <NavigationHandler />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route
            path="/"
            element={
              <ProtectedRoute>
                <Home />
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
