import { configureStore } from "@reduxjs/toolkit";
import { reducer as formReducer } from "redux-form";

import tabHoverReducer from "../slice/event/tab-hover-slice";

import cartReducer from "../slice/screen/cart";
import googleAuthReducer from "../slice/screen/google-auth-slice";
import searchSuggestionsReducer from "../slice/screen/search-suggestions-slice";
import signInReducer from "../slice/screen/sign-in-slice";
import tabsDataReducer from "../slice/screen/tabs-data-slice";



const store = configureStore({
  reducer: {
    form: formReducer,

    tabHover: tabHoverReducer,

    cart: cartReducer,
    googleAuth: googleAuthReducer,
    searchSuggestions: searchSuggestionsReducer,
    signIn: signInReducer,
    tabsData: tabsDataReducer,
  },
  
  devTools: process.env.NODE_ENV !== "prod"
});

export default store