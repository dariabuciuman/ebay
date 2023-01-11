import React from "react";
import { useEffect } from "react";
import axios from "axios";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { Button, CardActionArea, CardActions } from "@mui/material";
import { Link } from "react-router-dom";
import "./MyAccount.css";

function MyAccount() {
  const [user, setUser] = React.useState(null);
  const [products, setProducts] = React.useState([]);
  const [bids, setBids] = React.useState([]);

  useEffect(() => {
    const jwt = "Bearer " + localStorage.getItem("auth-token");
    if (localStorage.getItem("auth-token")) {
      axios({
        method: "get",
        url: `/api/private/user/info`,
        headers: {
          Authorization: jwt,
        },
      })
        .then((response) => {
          setUser(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      //TODO make call to get products posted by user and bids made by user4
      axios({
        method: "get",
        url: `/api/private/user/products`,
        headers: {
          Authorization: jwt,
        },
      })
        .then((response) => {
          setProducts(response.data);
        })
        .catch((error) => {
          console.log(error);
        });

      axios({
        method: "get",
        url: `/api/private/user/bids`,
        headers: {
          Authorization: jwt,
        },
      })
        .then((response) => {
          setBids(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, []);

  return (
    <div>
      {user && (
        <div className="user-page">
          <h2 className="user-info">Welcome, {user.firstName}!</h2>
          {products.length !== 0 && <p className="list-text">You can see your listed products below. </p>}
          {products.length === 0 && <p className="list-text">You don't have any listed products yet. </p>}
          {products.length !== 0 && (
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
                          DELETE
                        </Button>
                      </CardActions>
                    </Card>
                  </Link>
                </div>
              ))}
            </div>
          )}

          {bids.length !== 0 && <p className="list-text">You can see the products you placed a bid on. </p>}
          {bids.length === 0 && <p className="list-text">You made no bids yet. </p>}
          {bids.length !== 0 && (
            <div className="products">
              {bids.map((bid, index) => (
                <div key={index}>
                  <Link
                    style={{ textDecoration: "none" }}
                    to={`/products/${bid.name}`}
                    state={{ product: bid }}
                    onClick={() => {
                      console.log(`/products/${bid.name}`);
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
                          src={`https://source.unsplash.com/random/?${bid.name.split(" ")[0]}`}
                          alt={bid.name}
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
                            {bid.name}
                          </Typography>
                          <Typography
                            sx={{ fontSize: "16px", paddingLeft: "10px", paddingRight: "10px", fontWeight: "400", overflow: "hidden", maxHeight: "80px" }}
                            gutterBottom
                            variant="h6"
                            component="div"
                          >
                            {bid.description}
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
                          {bid.startingPrice + " $"}
                        </Typography>
                        <Button variant="contained" sx={{ backgroundColor: "black" }}>
                          WATCH
                        </Button>
                      </CardActions>
                    </Card>
                  </Link>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {!user && <div>Not logged in</div>}
    </div>
  );
}

export default MyAccount;
