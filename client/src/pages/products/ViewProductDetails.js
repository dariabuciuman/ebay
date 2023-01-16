import React from "react";
import { useLocation } from "react-router-dom";
import "./ViewProductDetails.css";
import axios from "axios";
import TextField from "@mui/material/TextField";
import { Alert } from "@mui/material";
import { Client } from "@stomp/stompjs";
import { useEffect } from "react";

function ViewProduct(props) {
  const location = useLocation();
  const { state } = location;
  const [bid, setBid] = React.useState(0);
  const [alert, setAlert] = React.useState(false);
  const [alertContent, setAlertContent] = React.useState("");
  const [formState, setFormState] = React.useState("error");
  const [currentHighestPrice, setCurrentHighestPrice] = React.useState(0);
  const [product, setProduct] = React.useState(null);
  const [user, setUser] = React.useState(null);
  const client = React.useRef(null);

  console.log(state.product);
  const WEBSOCKET_URL = "ws://localhost:8080/ws-message";

  const jwt = "Bearer " + localStorage.getItem("auth-token");

  const isLoggedIn = localStorage.getItem("auth-token") !== null;

  useEffect(() => {
    getUserData();
    if (state.product.active === false) {
      setCurrentHighestPrice(state.product.highestPrice);
      setProduct(state.product);
    } else {
      axios({
        method: "get",
        url: `/api/public/products/getProduct/${state.product.productId}`,
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          setProduct(response.data);
          setCurrentHighestPrice(response.data.highestPrice);
        })
        .catch((error) => {
          console.log(error);
        });
    }
    client.current = new Client({
      brokerURL: WEBSOCKET_URL,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: onConnected,
      onDisconnect: onDisconnected,
    });
    client.current.activate();
  }, []);

  const onConnected = () => {
    console.log("Connected to websocket...");
    client.current.subscribe(`/product_update/${state.product.productId}`, (message) => {
      if (message.body) {
        const newPrice = JSON.parse(message.body).highestPrice;
        console.log("Price:" + newPrice);
        setCurrentHighestPrice(newPrice);
      }
    });
  };

  const onDisconnected = () => {
    console.log("Disconnected");
  };

  function addBid(event) {
    setBid(event.target.value);
  }

  function bidForProduct() {
    if (user.userID === product.sellerId) {
      setFormState("error");
      setAlertContent("You can not place a bid on your own product");
      setAlert(true);
      return;
    }
    if (bid <= product.startingPrice) {
      setFormState("error");
      setAlert(true);
      setAlertContent("Bidding price cannot be lower than starting price");
      return;
    }
    if (bid <= product.highestPrice) {
      setFormState("error");
      setAlert(true);
      setAlertContent("There is already a higher price for this product");
      return;
    }
    product.highestPrice = +bid;

    const bidDTO = { productId: product.productId, highestPrice: bid };
    client.current.publish({ destination: "/product_update/" + product.productId, body: JSON.stringify(bidDTO) });

    axios({
      method: "post",
      url: `/api/private/products/bid`,
      data: product,
      headers: {
        "Content-Type": "application/json",
        Authorization: jwt,
      },
    })
      .then((response) => {
        console.log(response);
        setFormState("success");
        setAlertContent("Bid was successful");
        setAlert(true);
        console.log(bid);
        product.highestPrice = +bid;
      })
      .catch((error) => {
        setFormState("error");
        setAlertContent(error.response.data.message);
        setAlert(true);
      });
    console.log(product);
    console.log(bid);
  }

  function getUserData() {
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
    }
  }

  return (
    <div className="product-page">
      {product && (
        <div className="product-dash">
          <div className="product-img-area">
            <img src={`https://source.unsplash.com/random/?${product.name.split(" ")[0]}`} className="product-img" alt={product.name}></img>
          </div>
          <div className="product-text">
            <h1>{product.manufacturer}</h1>
            <h2> {product.name} </h2>
            <p>{product.description}</p>
            <div className="product-prices">
              <div className="product-price">
                <p>{"Starting price: "}</p>
                <h2> {" " + product.startingPrice} $ </h2>
              </div>
              {currentHighestPrice !== 0 && (
                <div className="product-price">
                  <p>{"Highest price: "}</p>
                  <h2 className="highest-price"> {" " + currentHighestPrice} $ </h2>
                </div>
              )}
            </div>
            {state.product.active ? (
              <p>Product listing expires in {product.expiryDate.substring(0, 10)}</p>
            ) : (
              <p>Product expired in {product.expiryDate.substring(0, 10)}</p>
            )}

            {state.product.active && isLoggedIn ? (
              <div className="bidding">
                <h3>Bid for this product: </h3>
                <TextField
                  id="standard-number"
                  type="number"
                  placeholder="Your bid"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  variant="standard"
                  onChange={addBid}
                />
              </div>
            ) : (
              <></>
            )}
            {state.product.active && isLoggedIn && user && user.userID === product.sellerId ? (
              <div className="product-buttons">
                <button
                  className="bid-button"
                  type="submit"
                  onClick={() => {
                    console.log("clicked " + product.name);
                    bidForProduct();
                  }}
                >
                  Place bid
                </button>
              </div>
            ) : (
              <p></p>
            )}

            {alert && (
              <Alert sx={{ mt: 3 }} severity={formState}>
                {alertContent}
              </Alert>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default ViewProduct;
