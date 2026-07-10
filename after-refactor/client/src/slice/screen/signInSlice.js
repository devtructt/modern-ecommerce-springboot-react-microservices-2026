import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isSignedIn: false,
  token: null,
  firstName: null,
  errorMessage: null,
  timestamp: null,
};

const signInSlice = createSlice({
  name: 'signIn',
  initialState,
  reducers: {
    signIn: (state, action) => {
      state.isSignedIn = true;
      state.token = action.payload.jwt;
      state.firstName = action.payload.firstName;
      state.timestamp = Date.now();
    },

    signOut: (state) => {
      state.isSignedIn = false;
      state.token = null;
      state.firstName = null;
      state.timestamp = Date.now();
    },

    setAuthError: (state, action) => {
      state.errorMessage = action.payload;
      state.timestamp = Date.now();
    },

    clearAuthError: (state) => {
      state.errorMessage = null;
      state.timestamp = Date.now();
    }
  },
});

export const { signIn, signInError, resetSignInError, signOut } = signInSlice.actions;
export default signInSlice.reducer;