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
  const client = React.useRef(null);

  const WEBSOCKET_URL = "ws://localhost:8080/ws-message";

  console.log(state.product);
  const jwt = "Bearer " + localStorage.getItem("auth-token");

  useEffect(() => {
    axios({
      method: "get",
      url: `/api/private/products/getProduct/${state.product.productId}`,
      headers: {
        "Content-Type": "application/json",
        Authorization: jwt,
      },
    })
      .then((response) => {
        setProduct(response.data);
        setCurrentHighestPrice(response.data.highestPrice);
      })
      .catch((error) => {
        console.log(error);
      });
    console.log(product);
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
        console.log("Price:" + JSON.parse(message.body).highestPrice);
        setCurrentHighestPrice(JSON.parse(message.body).highestPrice);
        product.highestPrice = +JSON.parse(message.body).highestPrice;
      }
    });
  };

  const onDisconnected = () => {
    console.log("Disconnected");
  };

  function addBid(event) {
    setBid(event.target.value);
  }

  function bidForProduct(productId) {
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
        console.log(bid);
        product.highestPrice = +bid;
      })
      .catch((error) => {
        setFormState("error");
        setAlertContent(error.response.data.message);
      });

    setAlert(true);
    console.log(product);
    console.log(bid);
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
            <p>Product listing expires in {product.expiryDate.substring(0, 10)}</p>
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
            <div className="product-buttons">
              <button
                className="bid-button"
                type="submit"
                onClick={() => {
                  console.log("clicked " + product.name);
                  bidForProduct(product.productId);
                }}
              >
                Place bid
              </button>
            </div>
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