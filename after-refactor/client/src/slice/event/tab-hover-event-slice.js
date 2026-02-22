import { createSlice } from '@reduxjs/toolkit';

const initialState = {tabIndex: false, hover: false, tabColor: 'black'};

const tabHoverEventSlice = createSlice({
    name: 'tabHoverEventSlice',
    initialState,
    reducers: {
      handleTabHoverEvent: (state, action) => Object.assign(state, action.payload),
    }
});

export const { handleTabHoverEvent } = tabHoverEventSlice.actions;
export default tabHoverEventSlice.reducer;