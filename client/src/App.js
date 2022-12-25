import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Login from "./pages/authentication/Login";
import Register from "./pages/authentication/Register";
import AddProduct from "./pages/products/AddProduct";
import ViewProducts from "./pages/products/ViewProducts";

function App() {
  const [user, setUser] = React.useState(false);

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
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
