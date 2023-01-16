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
    }
  }, []);

  return (
    <div>
      {user && (
        <div className="user-page">
          <h2 className="user-info">Welcome, {user.firstName}!</h2>
        </div>
      )}
      <Typography></Typography>
      {!user && <div>Not logged in</div>}
    </div>
  );
}

export default MyAccount;
