import React, { useState } from "react";
import axios from "axios";
import { useEffect } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { Button, CardActionArea, CardActions } from "@mui/material";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./ViewProducts.css";

function ViewProducts(props) {
  const [products, setProducts] = React.useState([]);
  const location = useLocation();
  const navigate = useNavigate();

  console.log(location.state);

  async function handleSearch(query) {
    await axios({
      method: "get",
      url: `/api/public/products/search`,
      params: { query: query },
    })
      .then((response) => {
        setProducts(response.data);
        //navigate("/products", { state: { query: query } });
        //window.location.reload();
      })
      .catch((error) => {
        console.log(error);
      });
  }

  async function getProducts() {
    await axios
      .get("/api/public/products", {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        setProducts(response.data);
      });
  }

  useEffect(() => {
    getProducts();
  }, []);

  useEffect(() => {
    if (location.state && location.state.query) handleSearch(location.state.query);
    else getProducts();
  }, [location]);

  return (
    <div className="shop">
      <div className="products">
        {products && products.length === 0 ? <p style={{ color: "black" }}>No products found.</p> : <></>}
        {products.map((product, index) => (
          <div key={index}>
            <Link
              style={{ textDecoration: "none" }}
              to={`/products/${product.name}`}
              state={{ product: product }}
              onClick={() => {
                console.log(`/products/${product.name}`);
              }}
            >
              <Card
                sx={{
                  width: 350,
                  height: 500,
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "space-between",
                  textDecoration: "none",
                }}
              >
                <CardActionArea>
                  <CardMedia
                    component="img"
                    height="230"
                    src={`https://source.unsplash.com/random/?${product.name.split(" ")[0]}`}
                    alt={product.name}
                    sx={{ objectFit: "contain" }}
                  />
                  <CardContent
                    sx={{
                      margin: -1,
                      color: "#000",
                    }}
                  >
                    <Typography
                      sx={{ fontSize: "18px", padding: "10px", fontWeight: "600", overflow: "hidden", maxHeight: "80px" }}
                      gutterBottom
                      variant="h6"
                      component="div"
                    >
                      {product.name}
                    </Typography>
                    <Typography
                      sx={{ fontSize: "16px", paddingLeft: "10px", paddingRight: "10px", fontWeight: "400", overflow: "hidden", maxHeight: "80px" }}
                      gutterBottom
                      variant="h6"
                      component="div"
                    >
                      {product.description}
                    </Typography>
                  </CardContent>
                </CardActionArea>
                <CardActions
                  sx={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "space-between",
                    margin: "0.5rem",
                  }}
                >
                  <div style={{ display: "flex", flexDirection: "row" }}>
                    <p style={{ fontSize: "14px" }}>Highest price:</p>
                    {product.highestPrice !== 0 ? (
                      <Typography sx={{ alignContent: "center", marginLeft: "20px", marginTop: "5px" }} variant="h5" color="#000">
                        {product.highestPrice + " $"}
                      </Typography>
                    ) : (
                      <Typography sx={{ alignContent: "center", marginLeft: "20px", marginTop: "5px" }} variant="h5" color="#000">
                        {product.startingPrice + " $"}
                      </Typography>
                    )}
                  </div>
                  <Button variant="contained" sx={{ backgroundColor: "black" }}>
                    BID
                  </Button>
                </CardActions>
              </Card>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ViewProducts;
