import React from "react";
import { useLocation } from "react-router-dom";
import "./ViewProductDetails.css";
import axios from "axios";
import TextField from "@mui/material/TextField";
import { Alert } from "@mui/material";

function ViewProduct(props) {
  const location = useLocation();
  const { state } = location;
  const [bid, setBid] = React.useState(0);
  const [alert, setAlert] = React.useState(false);
  const [alertContent, setAlertContent] = React.useState("");
  const [formState, setFormState] = React.useState("error");

  console.log(state.product);
  const jwt = "Bearer " + localStorage.getItem("auth-token");

  function addBid(event) {
    setBid(event.target.value);
  }

  function bidForProduct(productId) {
    if (bid <= state.product.startingPrice) {
      setFormState("error");
      setAlert(true);
      setAlertContent("Bidding price cannot be lower than starting price");
      return;
    }
    if (bid <= state.product.highestPrice) {
      setFormState("error");
      setAlert(true);
      setAlertContent("There is already a higher price for this product");
      return;
    }
    let product = structuredClone(state.product);
    product.highestPrice = +bid;
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
        state.product.highestPrice = +bid;
      })
      .catch((error) => {
        setFormState("error");
        setAlertContent(error.response.data.message);
      });

    setAlert(true);
    console.log(state.product);
    console.log(bid);
  }

  return (
    <div className="product-page">
      <div className="product-dash">
        <div className="product-img-area">
          <img src={`https://source.unsplash.com/random/?${state.product.name.split(" ")[0]}`} className="product-img" alt={state.product.name}></img>
        </div>
        <div className="product-text">
          <h1>{state.product.manufacturer}</h1>
          <h2> {state.product.name} </h2>
          <p>{state.product.description}</p>
          <div className="product-prices">
            <div className="product-price">
              <p>{"Starting price: "}</p>
              <h2> {" " + state.product.startingPrice} $ </h2>
            </div>
            {state.product.highestPrice !== 0 && (
              <div className="product-price">
                <p>{"Highest price: "}</p>
                <h2 className="highest-price"> {" " + state.product.highestPrice} $ </h2>
              </div>
            )}
          </div>
          <p>Product listing expires in {state.product.expiryDate.substring(0, 10)}</p>
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
                console.log("clicked " + state.product.name);
                bidForProduct(state.product.productId);
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
    </div>
  );
}

export default ViewProduct;
