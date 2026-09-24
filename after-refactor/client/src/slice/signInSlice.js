import { createSlice } from '@reduxjs/toolkit';
import { Cookie } from 'js-cookie';

import { AUTH_DETAILS_COOKIE } from '../constant/cookie'

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
      state.errorMessage = null;
      state.timestamp = Date.now();
    },

    signOut: (state) => {
      Cookies.remove(AUTH_DETAILS_COOKIE);
      state.isSignedIn = false;
      state.token = null;
      state.firstName = null;
      state.errorMessage = null;
      state.timestamp = Date.now();
    },

    setAuthError: (state, action) => {
      state.isSignedIn = false
      state.errorMessage = action.payload;
      state.timestamp = Date.now();
    },

    clearAuthError: (state) => {
      state.errorMessage = null;
    }
  },
});

export const { signIn, signOut, setAuthError, clearAuthError } = signInSlice.actions;
export default signInSlice.reducer;