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
    handleSignIn: (state, action) => Object.assign(state, {
      isSignedIn: true,
      token: action.payload.jwt,
      firstName: action.payload.firstName,
      timestamp: Date.now(),
    }),

    handleSignInError: (state, action) => Object.assign(state, {
      isSignedIn: false,
      errorMessage: action.payload.errorMessage,
      timestamp: Date.now(),
    }),

    resetSignInError: (state) => Object.assign(state, {
      errorMessage: null,
      timestamp: Date.now(),
    }),

    handleSignOut: (state) => Object.assign(state, {
      isSignedIn: false,
      token: null,
      firstName: null,
      timestamp: Date.now(),
    }),
  },
});

export const { handleSignIn, handleSignInError, resetSignInError, handleSignOut } = signInSlice.actions;
export default signInSlice.reducer;