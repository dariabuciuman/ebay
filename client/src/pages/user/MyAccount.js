import React from "react";
import { useEffect } from "react";
import axios from "axios";
import "./MyAccount.css";
import { TextField } from "@mui/material";

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
          <div className="user-info">
            <h2>Welcome, {user.firstName}!</h2>
            <TextField
              id="outlined-read-only-input"
              label="First Name"
              defaultValue={user.firstName}
              InputProps={{
                readOnly: true,
              }}
              sx={{ marginBottom: "20px" }}
            />
            <TextField
              id="outlined-read-only-input"
              label="Last Name"
              defaultValue={user.lastName}
              InputProps={{
                readOnly: true,
              }}
              sx={{ marginBottom: "20px" }}
            />
            <TextField
              id="outlined-read-only-input"
              label="Username"
              defaultValue={user.username}
              InputProps={{
                readOnly: true,
              }}
              sx={{ marginBottom: "20px" }}
            />
            <TextField
              id="outlined-read-only-input"
              label="Email"
              defaultValue={user.email}
              InputProps={{
                readOnly: true,
              }}
              sx={{ marginBottom: "20px" }}
            />
          </div>
        </div>
      )}
      {!user && <div>Not logged in</div>}
    </div>
  );
}

export default MyAccount;
