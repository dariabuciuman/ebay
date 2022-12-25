import React from "react";
import axios from "axios";

function Home() {
  const [products, setProducts] = React.useState([]);

  async function getProducts() {
    await axios
      .get("/api/public/products", {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log(response);
      });
  }

  const handleGetProducts = () => {
    getProducts();
  };

  return (
    <div>
      Home page
      <button onClick={getProducts}>Get products</button>
    </div>
  );
}

export default Home;
