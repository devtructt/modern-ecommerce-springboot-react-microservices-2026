import React from 'react'
import { Drawer } from '@mui/material';

import AccordionSection from './AccordionSection';
import useClickAway from '../../../hook/useClickAway';

const SideBar = props => {
  useClickAway(props.closeHandler);

  return (
    <Drawer
      variant='temporary'
      open={props.open}
      slotProps={{
        paper: {
          sx: {
            width: { xs: 280, sm: 400 },
            WebkitOverflowScrolling: 'touch',  // Smooth scroll trên iOS/mobile
            msOverflowStyle: 'none',  // IE/Edge
            scrollbarWidth: 'none',   // Firefox
            '&::-webkit-scrollbar': {  // Chrome/Safari
              display: 'none',
            },
          }
        }
      }}
    >
      <AccordionSection />
    </Drawer>
  )
}

export default SideBar;