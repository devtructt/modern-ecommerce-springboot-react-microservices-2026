import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isError: false,
  errorMessage: null,
  data: [],
};

const searchSuggestionsSlice = createSlice({
  name: 'searchSuggestions',
  initialState,
  reducers: {
    fetchSearchSuggestionsSuccess: (state, action) => {
      state.data = action.payload;
      state.isError = false;
      state.errorMessage = null;
    },
    fetchSearchSuggestionsFailed: (state) => {
      state.isError = true;
      state.errorMessage = 'Something went wrong';
    },
  },
});

export const { fetchSearchSuggestionsSuccess, fetchSearchSuggestionsFailed } = searchSuggestionsSlice.actions;
export default searchSuggestionsSlice.reducer;