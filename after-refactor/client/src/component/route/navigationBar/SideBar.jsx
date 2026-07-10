import React, { useRef } from 'react';
import { Box, Drawer } from '@mui/material';

import AccordionSection from './AccordionSection';
import { useClickAway } from '../../../hook/useClickAway';

function SideBar({ open, closeHandler }) {
  const drawerRef = useRef(null);

  useClickAway(drawerRef, closeHandler);

  return (
    <Box sx={{ display: 'flex' }}>
      <Drawer
        ref={drawerRef}
        variant="temporary"
        open={open}
        sx={{
          width: { xs: 280, sm: 400 },
          flexShrink: 0,
        }}
        slotProps={{
          paper: {
            sx: {
              width: { xs: 280, sm: 400 },
              WebkitOverflowScrolling: 'touch',
              msOverflowStyle: 'none',
              scrollbarWidth: 'none',
              '&::-webkit-scrollbar': { display: 'none' }
            },
          },
        }}
      >
        <AccordionSection />
      </Drawer>
    </Box>
  );
}

export default React.memo(SideBar);