import { configureStore } from "@reduxjs/toolkit";
import { reducer as formReducer } from "redux-form";

import tabHoverEventReducer from "../slice/event/tab-hover-event-slice";

import cartReducer from "../slice/screen/cart";
import googleAuthReducer from "../slice/screen/google-auth-slice";
import searchKeywordReducer from "../slice/screen/search-keyword-slice";
import signInReducer from "../slice/screen/sign-in-slice";
import tabsDataReducer from "../slice/screen/tabs-data-slice";



const store = configureStore({
  reducer: {
    form: formReducer,

    tabHoverEvent: tabHoverEventReducer,

    cart: cartReducer,
    googleAuth: googleAuthReducer,
    searchKeyword: searchKeywordReducer,
    signIn: signInReducer,
    tabsData: tabsDataReducer,
  },
  devTools: process.env.NODE_ENV !== "prod"
});

export default store