export const axiosBaseQuery = (axiosInstance) => async ({ url, method = 'get', data, params }) => {
  try {
    const response = await axiosInstance({ url, method, data, params });
    return { data: response.data };
  } catch (error) {
    return { error: { status: error.response?.status, data: error.response?.data || error.message } };
  }
}