import React from "react";
import { Navigate, Outlet } from "react-router-dom";

const useAuthentication = () => {
  let isLoggedIn = false;
  if (localStorage.getItem("auth-token") != null) {
    isLoggedIn = true;
  }
  const user = { loggedIn: isLoggedIn };
  return user && user.loggedIn;
};

const ProtectedRoutes = () => {
  const isAuth = useAuthentication();
  return isAuth ? <Outlet /> : <Navigate to="/signin" />;
};

export default ProtectedRoutes;
