import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import MenuItem from "@mui/material/MenuItem";
import { Grid, Container, Typography, Stack, InputAdornment, Alert } from "@mui/material";
import axios from "axios";

export default function AddProduct() {
  const [name, setName] = React.useState("");
  const [manufacturer, setManufacturer] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [condition, setCondition] = React.useState([]);
  const [startingPrice, setStartingPrice] = React.useState(0);
  const [expiryDate, setExpiryDate] = React.useState("");
  const [formState, setFormState] = React.useState("error");
  const [alert, setAlert] = React.useState(false);
  const [alertContent, setAlertContent] = React.useState("");
  const publishDate = new Date().toISOString().slice(0, 10);
  console.log(publishDate);

  const conditionsList = [
    {
      value: "New",
      label: "New",
    },
    {
      value: "Used",
      label: "Used",
    },
  ];

  const addName = (event) => {
    console.log(event.target.value);
    setName(event.target.value);
  };
  const addManufacturer = (event) => {
    console.log(event.target.value);
    setManufacturer(event.target.value);
  };
  const addDescription = (event) => {
    console.log(event.target.value);
    setDescription(event.target.value);
  };
  const addCondition = (event) => {
    console.log(event.target.value);
    setCondition(event.target.value);
  };
  const addStartingPrice = (event) => {
    console.log(event.target.value);
    setStartingPrice(event.target.value);
  };
  const addExpiryDate = (event) => {
    console.log(event.target.value);
    setExpiryDate(event.target.value);
  };

  const resetForm = () => {
    setName("");
    setManufacturer("");
    setDescription("");
    setCondition("");
    setStartingPrice(0);
    setExpiryDate("");
  };

  async function postProduct(product) {
    const jwt = "Bearer " + localStorage.getItem("auth-token");
    await axios
      .post("/api/public/products/add", product, {
        headers: {
          "Content-Type": "application/json",
          Authorization: jwt,
        },
      })
      .then((response) => {
        if ((response.status = 200)) {
          setAlertContent("Product was added succesfully");
          setAlert(true);
          setFormState("success");
        }
      })
      .catch((error) => {
        setAlertContent(`Product could not be added: ${error.response.statusText}`);
        setAlert(true);
        setFormState("error");
      });
  }

  const onSubmit = (event) => {
    event.preventDefault();
    const product = {
      name: name,
      manufacturer: manufacturer,
      description: description,
      condition: condition,
      startingPrice: startingPrice,
      publishDate: publishDate,
      expiryDate: expiryDate,
      isActive: true,
    };
    console.log(product);
    postProduct(product);
  };

  return (
    <div>
      <Container maxWidth="xs" sx={{ mt: 6, mb: 6 }}>
        <Typography sx={{ mb: 2, textAlign: "center" }} variant="h4" gutterBottom>
          Add your product here:
        </Typography>
        <Box component="form" noValidate onSubmit={onSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField fullWidth required label="Title" variant="outlined" name="title" onChange={addName} />
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth required label="Manufacturer" variant="outlined" name="manufacturer" onChange={addManufacturer} />
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth required multiline maxRows={4} label="Description" variant="outlined" name="description" onChange={addDescription} />
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth required select label="Condition" variant="outlined" name="condition" onChange={addCondition}>
                {conditionsList.map((option) => (
                  <MenuItem key={option.value} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                required
                InputProps={{
                  startAdornment: <InputAdornment position="start">$</InputAdornment>,
                }}
                type="number"
                label="Price"
                variant="outlined"
                name="price"
                onChange={addStartingPrice}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                InputLabelProps={{
                  shrink: true,
                }}
                inputProps={{
                  min: publishDate,
                }}
                fullWidth
                required
                type="date"
                label="ExpiryDate"
                variant="outlined"
                name="expiryDate"
                onChange={addExpiryDate}
              />
            </Grid>
          </Grid>
          <Stack sx={{ mt: 3 }} direction="row" justifyContent="flex-end" spacing={2}>
            <Button color={"error"} variant="outlined" type="button" onClick={resetForm}>
              Cancel
            </Button>
            <Button sx={{ backgroundColor: "black", ":hover": { backgroundColor: "darkslategray" } }} variant="contained" type="submit">
              Add Product
            </Button>
            {/* TODO
             * Add alert if request is succesful
             */}
          </Stack>
          {alert && (
            <Alert sx={{ mt: 3 }} severity={formState}>
              {alertContent}
            </Alert>
          )}
        </Box>
      </Container>
    </div>
  );
}
