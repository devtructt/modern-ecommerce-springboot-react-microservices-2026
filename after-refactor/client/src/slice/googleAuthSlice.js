import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

const initialState = {
  isSignedIn: false,
  firstName: null,
  oAuth: null,
};

const signOutUsingOAuth = createAsyncThunk('googleAuth/signOutUsingOAuth', async (oAuth) => {
    if (!oAuth.isSignedIn.get()) {
      return false;
    }
    await oAuth.signOut();
    return !oAuth.isSignedIn.get();
  }
);

const googleAuthSlice = createSlice({
  name: 'googleAuth',
  initialState,
  reducers: {
    signIn: (state, action) => {
      const { firstName, oAuth } = action.payload;
      state.isSignedIn = true;
      state.firstName = firstName;
      state.oAuth = oAuth;
    },

    signOut: (state) => {
      state.isSignedIn = false;
      state.firstName = null;
      state.oAuth = null;
    },

    setAuth: (state, action) => {
      const { firstName, oAuth } = action.payload;
      state.isSignedIn = oAuth.isSignedIn.get();
      state.firstName = firstName;
      state.oAuth = oAuth;
    }
  },
  extraReducers: (builder) => {
    builder.addCase(signOutUsingOAuth.fulfilled, (state, action) => {
      if (!action.payload) {
        return;
      }
      state.isSignedIn = false;
      state.firstName = null;
    });
  }
});

export { signOutUsingOAuth };
export const { setAuth, signIn, signOut } = googleAuthSlice.actions;
export default googleAuthSlice.reducer;