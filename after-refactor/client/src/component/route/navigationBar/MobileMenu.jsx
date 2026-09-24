import React from 'react';
import { Grid, IconButton, Menu, MenuItem, Typography } from '@mui/material';
import BagButton from './bagButton';

const menuItemSx = {
  padding: '0 0.7rem 0 0',
};

function MobileMenu({
  isMobileMenuOpen,
  mobileMoreAnchorEl,
  onClose,
  onAuthButtonClick,
  onBagButtonClick,
  authIcon,
  authLabel
}) {
  return (
    <Menu
      open={isMobileMenuOpen}
      anchorEl={mobileMoreAnchorEl}
      anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
      transformOrigin={{ vertical: 'top', horizontal: 'right' }}
      keepMounted
      onClose={onClose}
    >
      <MenuItem onClick={onAuthButtonClick} sx={menuItemSx}>
        <Grid container alignItems="center">
          <Grid item>
            <IconButton color="inherit">
              {authIcon}
            </IconButton>
          </Grid>
          <Grid item>
            <Typography>{authLabel}</Typography>
          </Grid>
        </Grid>
      </MenuItem>
      <MenuItem onClick={onBagButtonClick} sx={menuItemSx}>
        <Grid container alignItems="center">
          <Grid item xs={7}>
            <IconButton color="inherit">
              <BagButton />
            </IconButton>
          </Grid>
          <Grid item>
            <Typography>Bag</Typography>
          </Grid>
        </Grid>
      </MenuItem>
    </Menu>
  );
}

export default MobileMenu;