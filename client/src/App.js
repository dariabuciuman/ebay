import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Login from "./pages/authentication/Login";
import Register from "./pages/authentication/Register";
import AddProduct from "./pages/products/AddProduct";
import ViewProducts from "./pages/products/ViewProducts";
import ViewProduct from "./pages/products/ViewProductDetails";
import MyAccount from "./pages/user/MyAccount";
import MyProducts from "./pages/user/MyProducts";
import MyBids from "./pages/user/MyBids";
import MyExpiredProducts from "./pages/user/MyExpiredProducts";
import MyExpiredBids from "./pages/user/MyExpiredBids";
import ProtectedRoutes from "./utils/ProtectedRoutes";

function App() {
  return (
    <>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/signin" element={<Login />} />
          <Route path="/signup" element={<Register />} />
          <Route path="/" element={<Home />} />
          <Route path="/products" element={<ViewProducts />} />
          <Route path="/products/:id" element={<ViewProduct />} />
          <Route element={<ProtectedRoutes />}>
            <Route path="/products/add" element={<AddProduct />} />
            <Route path="/account" element={<MyAccount />} />
            <Route path="/account/products" element={<MyProducts />} />
            <Route path="/account/bids" element={<MyBids />} />
            <Route path="/account/expired-products" element={<MyExpiredProducts />} />
            <Route path="/account/expired-bids" element={<MyExpiredBids />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
