import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isSignedIn: false,
  firstName: null,
  authInstance: null,
};

const googleAuthSlice = createSlice({
  name: 'googleAuth',
  initialState,
  reducers: {
    setGoogleAuth: (state, action) => {
      const { firstName, authInstance } = action.payload;
      state.isSignedIn = authInstance.isSignedIn;
      state.firstName = firstName;
      state.authInstance = authInstance;
    },
    signInGoogleAuth: (state, action) => {
      const { firstName, authInstance } = action.payload;
      state.isSignedIn = true;
      state.firstName = firstName;
      state.authInstance = authInstance;
    },
    signOutGoogleAuth: (state) => {
      state.isSignedIn = false;
      state.firstName = null;
      state.authInstance = null;
    },
  },
});

export const { setGoogleAuth, signInGoogleAuth, signOutGoogleAuth } = googleAuthSlice.actions;
export default googleAuthSlice.reducer;