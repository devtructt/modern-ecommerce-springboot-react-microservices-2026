import React from 'react';
import { Grid, Typography } from '@mui/material';

const ERROR_MESSAGES = {
  generic: 'Oops! Something went wrong...',
};

const ErrorMessage = () => {
  return (
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      sx={{ minHeight: '100vh', pt: 2 }}
    >
      <Grid sx={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
        <Typography
          variant="h3"
          color="error"
          sx={{ fontSize: { xs: '2rem', md: '3rem' }, textAlign: 'center' }}
        >
          {ERROR_MESSAGES.generic}
        </Typography>
      </Grid>
    </Grid>
  );
};

export default ErrorMessage;