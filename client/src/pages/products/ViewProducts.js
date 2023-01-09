import React from "react";
import axios from "axios";
import { useEffect } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { Button, CardActionArea, CardActions } from "@mui/material";
import { Link } from "react-router-dom";
import "./ViewProducts.css";

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
        setProducts(response.data);
      });
  }

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <div className="shop">
      <div className="products">
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
                  <Typography sx={{ alignContent: "center" }} variant="h5" color="#000">
                    {product.startingPrice + " $"}
                  </Typography>
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
