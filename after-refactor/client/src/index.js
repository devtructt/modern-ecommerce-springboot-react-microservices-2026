import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { ThemeProvider, CssBaseline } from '@mui/material';
import log from 'loglevel'

import ErrorBoundary from './ErrorBoundary'
import App from './App';
import theme from './theme';
import store from './store';

import './index.css';

if (process.env.REACT_APP_ENVIRONMENT === 'dev') {
  log.setLevel("info");
} else {
  console.log = console.error = console.warn = function () { };
  log.disableAll(true);
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Provider store={store}>
        <ErrorBoundary>
          <App />
        </ErrorBoundary>
      </Provider>
      </ThemeProvider>
  </React.StrictMode>
);