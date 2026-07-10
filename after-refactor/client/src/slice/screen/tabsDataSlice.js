import { createSlice } from '@reduxjs/toolkit';
import { data } from 'react-router-dom';

const initialState = { 
    isLoading: true,
    data: null
};

const tabsDataSlice = createSlice({
    name: 'tabsData',
    initialState,
    reducers: {
        loadTabsData: (state, action) => { 
            Object.assign(state, action.payload);
        }
    }
});

export const { loadTabsData } = tabsDataSlice.actions;
export default tabsDataSlice.reducer;