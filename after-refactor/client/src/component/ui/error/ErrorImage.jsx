import React from 'react';
import Grid from '@mui/material/Grid2';
import { Box } from '@mui/material';

function ErrorImage({ image, alt }) {
  return (
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      sx={{ minHeight: '60vh' }}
    >
      <Grid size={{ xs: 12, sm: 9, lg: 5 }}>
        <Box
          component="img"
          src={image}
          alt={alt}
          sx={{
            height: '50vh',
            width: '100%',
            objectFit: 'contain'
          }}
        />
      </Grid>
    </Grid>
  );
}

export default ErrorImage;