import { createSlice } from '@reduxjs/toolkit';

const initialState = { isLoading: true };

const tabsDataSlice = createSlice({
    name: 'tabsDataSlice',
    initialState,
    reducers: {
        loadTabsData: (state, action) => Object.assign(state, action.payload),
    }
});

export const { loadTabsData } = tabsDataSlice.actions;
export default tabsDataSlice.reducer;