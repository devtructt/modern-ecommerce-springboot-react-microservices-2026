import React, { useState, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { Autocomplete, Box, Grid, TextField } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CloseIcon from '@mui/icons-material/Close';

import { PRODUCT_BY_CATEGORY_DATA_API } from '../../../constant/api-route';
import { MAX_PRODUCTS_PER_PAGE } from '../../../constant/constant';

function SearchBar({device, size, getSearchSuggestions, handleClose}) {
  const [currentSelection, setCurrentSelection] = useState(null);

  const navigate = useNavigate();
  const searchSuggestions = useSelector(state => state.searchSuggestions);

  const getProductLink = useCallback((keyword) => {
    if (!keyword || !searchSuggestions?.data?.length) return null;

    const matchedItem = searchSuggestions.data.find(item => 
      item.keyword.toLowerCase() === keyword.toLowerCase()
    );

    return matchedItem ? matchedItem.link : null;
  }, [searchSuggestions]);

  const executeSearch = useCallback((keyword) => {
    if (!keyword?.trim()) return;

    const productLink = getProductLink(keyword);
    const searchQuery = productLink || keyword;

    navigate(`${PRODUCT_BY_CATEGORY_DATA_API}?q=${encodeURIComponent(searchQuery)}&page=0,${MAX_PRODUCTS_PER_PAGE}`);
  }, [navigate, getProductLink]);

  const handleSearchSubmit = useCallback((event, reason) => {
    if (reason === 'selectOption' || reason === 'blur') {
      const keyword = currentSelection?.keyword || currentSelection?.inputValue;
      if (keyword) {
        executeSearch(keyword);
      }
    }
  }, [currentSelection, executeSearch]);

  const handleInputChange = useCallback((event, newValue) => {
    getSearchSuggestions(newValue);
  }, [getSearchSuggestions]);

  const handleSelectionChange = useCallback((event, newValue) => {
    if (typeof newValue === 'string') {
      setCurrentSelection({ keyword: newValue });
    } else if (newValue?.inputValue) {
      setCurrentSelection({ keyword: newValue.inputValue });
    } else {
      setCurrentSelection(newValue);
    }
  }, []);

  const renderInputField = (params) => {
    if (device === 'mobile') {
      return (
        <TextField
          {...params}
          label="Search for products, brands and more"
          variant="outlined"
          sx={{
            position: 'absolute',
            left: 0,
            top: 15,
          }}
          slotProps={{
            input: {
              ...params.InputProps,
              startAdornment: (
                <ArrowBackIcon 
                  onClick={handleClose} 
                  fontSize="large" 
                  sx={{ cursor: 'pointer' }}
                />
              ),
            },
          }}
        />
      );
    }

    return (
      <TextField
        {...params}
        label="Search for products, brands and more"
        variant="outlined"
      />
    );
  };

  return (
    <Grid container alignItems="center">
      <Autocomplete
        id="search-bar"
        options={searchSuggestions?.data || []}
        freeSolo
        value={currentSelection}
        size={size}
        fullWidth
        autoComplete
        autoHighlight
        selectOnFocus
        clearOnBlur
        blurOnSelect
        handleHomeEndKeys
        getOptionLabel={(option) => {
          if (typeof option === 'string') return option;
          return option?.inputValue || option?.keyword || '';
        }}
        renderOption={(props, option) => (
          <Box component="li" {...props} sx={{ pl: { xs: 5 } }}>
            {option.keyword}
          </Box>
        )}
        renderInput={renderInputField}
        onInputChange={handleInputChange}
        onChange={handleSelectionChange}
        onClose={handleSearchSubmit}
        closeIcon={<CloseIcon />}
        slotProps={{
          paper: { sx: { height: 250 } },
          listbox: { sx: { maxHeight: 240 } },
        }}
      />
    </Grid>
  );
}

export default React.memo(SearchBar);
