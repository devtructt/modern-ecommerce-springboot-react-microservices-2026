import React from 'react';
import { Badge } from '@mui/material';
import LocalMallIcon from '@mui/icons-material/LocalMall';
import { useSelector } from 'react-redux';

const BagButton = () => {
  const cart = useSelector(state => state.cart);

  return (
    <Badge badgeContent={cart.totalQuantity} color='secondary'>
      <LocalMallIcon />
    </Badge>
  )
}

export default BagButton;