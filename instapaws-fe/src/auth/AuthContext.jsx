import { createContext, useContext, useEffect, useState } from "react";
import axiosApi from "../api/axiosApi";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);


  const login = async (username, password) => {
    const res = await axiosApi.post("/auth/login", { username, password });
    localStorage.setItem("token", res.data.token);
    setUser(res.data.user);
  };

  const fetchUser = async () => {
    try {
      const res = await axiosApi.get("/auth/me", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
      setUser(res.data);
    } catch {
      logout();
    } finally {
      setLoading(false);
    }
  };

  const register = async (username, password) => {
    await axiosApi.post("/auth/register", { username, password });
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    if (initialized) return;
    setInitialized(true);

    const token = localStorage.getItem("token");
    if (token) fetchUser();
    else setLoading(false);
  }, [initialized]);

  return (
    <AuthContext.Provider value={{ user, login, logout, register, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
