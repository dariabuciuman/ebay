import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Chip from "@mui/material/Chip";
import { Grid, Container, Typography, Stack } from "@mui/material";

export default function AddProduct() {
  const [title, setTitle] = React.useState("");
  const [manufacturer, setManufacturer] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [condition, setCondition] = React.useState([]);
  const [startingPrice, setStartingPrice] = React.useState(0);
  const [expiryDate, setExpiryDate] = React.useState("");
  const [formState, setFormState] = React.useState("error");

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

  const addTitle = (event) => {
    setTitle(event.target.value);
  };
  const addManufacturer = (event) => {
    setManufacturer(event.target.value);
  };
  const addDescription = (event) => {
    setDescription(event.target.value);
  };
  const addCondition = (event) => {
    setCondition(event.target.value);
  };
  const addStartingPrice = (event) => {
    setStartingPrice(event.target.value);
  };
  const addExpiryDate = (event) => {
    setExpiryDate(event.target.value);
  };

  const resetForm = () => {
    setTitle("");
    setManufacturer("");
    setDescription("");
    setCondition("");
    setStartingPrice(0);
    setExpiryDate("");
  };

  const onSubmit = (event) => {
    console.log("submitted");
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
              <TextField fullWidth label="Title" variant="outlined" name="title" onChange={addTitle} />
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth label="Manufacturer" variant="outlined" name="manufacturer" onChange={addManufacturer} />
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth label="Description" variant="outlined" name="description" onChange={addDescription} />
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth select label="Condition" variant="outlined" name="condition" onChange={addCondition}>
                {conditionsList.map((option) => (
                  <MenuItem key={option.value} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={12}>
              <TextField fullWidth type="number" label="Price" variant="outlined" name="price" onChange={addStartingPrice} />
            </Grid>
            <Grid item xs={12}>
              <TextField
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                type="datetime-local"
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
        </Box>
      </Container>
    </div>
  );
}
