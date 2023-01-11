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

function App() {
  return (
    <>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/signin" element={<Login />} />
          <Route path="/signup" element={<Register />} />
          <Route path="/" element={<Home />} />
          <Route path="/products/add" element={<AddProduct />} />
          <Route path="/products" element={<ViewProducts />} />
          <Route path="/products/:id" element={<ViewProduct />} />
          <Route path="/account" element={<MyAccount />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
