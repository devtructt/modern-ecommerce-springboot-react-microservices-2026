import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  hover: false,
  tabIndex: false,
};

const tabHoverSlice = createSlice({
  name: 'tabHover',
  initialState,
  reducers: {
    setTabHoverState: (state, action) => {
      Object.assign(state, action.payload);
    }
  }
});

export const { setTabHoverState } = tabHoverSlice.actions;
export default tabHoverSlice.reducer;