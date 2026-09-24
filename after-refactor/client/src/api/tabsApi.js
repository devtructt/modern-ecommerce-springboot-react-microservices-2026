import { createApi } from '@reduxjs/toolkit/query/react';
import { axiosBaseQuery } from './axiosBaseQuery';
import { commonDataApi } from './axiosInstance';

const tabsApi = createApi({
  reducerPath: 'tabsApi',
  baseQuery: axiosBaseQuery({ axiosInstance: commonDataApi }),
  endpoints: (builder) => ({
    getTabsData: builder.query({
      query: () => ({ url: '/tabs' }),
    }),
  }),
});

export const { useGetTabsDataQuery } = tabsApi;
export default tabsApi;