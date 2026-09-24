import { configureStore } from '@reduxjs/toolkit';
import { reducer as formReducer } from 'redux-form';

import tabHoverReducer from '../slice/event/tab-hover-slice';

import cartReducer from '../slice/screen/cart';
import googleAuthReducer from '../slice/screen/google-auth-slice';
import searchSuggestionsReducer from '../slice/screen/search-suggestions-slice';
import signInReducer from '../slice/screen/sign-in-slice';

import tabsApi from '../api/tabs-api';

const store = configureStore({
  reducer: {
    form: formReducer,

    
    signIn: signInReducer,
    googleAuth: googleAuthReducer,
    
    cart: cartReducer,
    searchSuggestions: searchSuggestionsReducer,
    tabHover: tabHoverReducer,
    [tabsApi.reducerPath]: tabsApi.reducer,
  },

  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(
    tabsApi.middleware,
  ),
  
  devTools: process.env.NODE_ENV !== 'prod'
});

export default store;