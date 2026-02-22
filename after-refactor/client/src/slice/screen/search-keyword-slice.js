import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isError: false,
  errorMessage: null,
  searchResults: [{ keyword: ' ' }],
};

const searchKeywordSlice = createSlice({
  name: 'searchKeyword',
  initialState,
  reducers: {
    searchKeywordSuccess: (state, action) => {
      state.searchResults = action.payload;
      state.isError = false;
      state.errorMessage = null;
    },
    searchKeywordError: (state) => {
      state.isError = true;
      state.errorMessage = 'Something went wrong';
    },
  },
});

export const { searchKeywordSuccess, searchKeywordError } = searchKeywordSlice.actions;
export default searchKeywordSlice.reducer;