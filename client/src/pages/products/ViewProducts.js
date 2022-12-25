import React from "react";
import axios from "axios";

function ViewProducts() {
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

  return (
    <div>
      ViewProducts
      <button onClick={getProducts}>Get products</button>
    </div>
  );
}

export default ViewProducts;
