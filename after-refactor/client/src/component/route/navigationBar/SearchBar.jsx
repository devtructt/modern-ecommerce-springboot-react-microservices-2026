import React, { useState } from 'react';
import {
  Autocomplete,
  Grid,
  TextField,
} from '@mui/material';
import {
  ArrowBackIcon,
  CloseIcon,
} from '@mui/icons-material/Close';
import { useSelector, connect } from 'react-redux';

import {PRODUCT_BY_CATEGORY_DATA_API} from "../../../constant/api-route";
import {MAX_PRODUCTS_PER_PAGE} from "../../../constant/constant";

const SearchBar = (props) => {
  const [value, setValue] = useState(null);
  const searchKeyword = useSelector(state => state.searchKeyword)
  let selectedValue = null

  return (
    <Grid container alignItems="center">
      
    </Grid>
  );
}

export default SearchBar;