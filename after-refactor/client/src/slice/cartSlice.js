import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  totalQuantity: 0,
  itemQuantities: {},
};

const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    addToCart: (state, action) => {
      Object.assign(state, action.payload);
    },
    resetCart: (state) => {
      state.totalQuantity = 0;
      state.itemQuantities = {};
    },
  },
});

export const { addToCart, resetCart } = cartSlice.actions;
export default cartSlice.reducer;