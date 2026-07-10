import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isSignedIn: false,
  isSignedInUsingOAuth: false,
  firstName: null,
  authInstance: null,
};

const googleAuthSlice = createSlice({
  name: 'googleAuth',
  initialState,
  reducers: {
    setAuth: (state, action) => {
      const { firstName, authInstance } = action.payload;
      state.isSignedIn = authInstance.isSignedIn.get();
      state.firstName = firstName;
      state.authInstance = authInstance;
    },
    signIn: (state, action) => {
      const { firstName, authInstance } = action.payload;
      state.isSignedIn = true;
      state.firstName = firstName;
      state.authInstance = authInstance;
    },
    signOut: (state) => {
      state.isSignedIn = false;
      state.firstName = null;
      state.authInstance = null;
    },
  },
});

export const { setAuth, signIn, signOut } = googleAuthSlice.actions;
export default googleAuthSlice.reducer;