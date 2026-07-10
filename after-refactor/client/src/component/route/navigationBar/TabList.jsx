import React, { useRef } from 'react'
import { Tabs, Tab } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';

import { setTabHoverState } from '../../../slice/event/tab-hover-event-slice';
import { TAB_CONFIG } from '../../../constant/constant';

const TabList = () => {
  const tabsRef = useRef(null);
  const dispatch = useDispatch();
  let { tabIndex } = useSelector(state => state.tabHover);
  const tabIndicatorColor = tabIndex ? TAB_CONFIG[tabIndex]?.color || 'transparent' : 'transparent';

  const handleMouseEnter = event => {
    const tabIndex = parseInt(event.currentTarget.dataset.index);
    dispatch(setTabHoverState({ hover: true, tabIndex }));
  }

  const handleMouseLeave = event => {
    const tabsRefRect = tabsRef.current?.getBoundingClientRect();
    if (!tabsRefRect) return;
    const { clientX, clientY } = event;
    if (clientX < tabsRefRect.left || clientX > tabsRefRect.right || clientY < tabsRefRect.top) {
      dispatch(setTabHoverState({ hover: false, tabIndex: false }));
    }
  }

  return (
    <Tabs
      ref={tabsRef}
      value={tabIndex}
      slotProps={{
        indicator: {
          sx: {
            height: '4px',
            backgroundColor: tabIndicatorColor,
          }
        }
      }}
    >
      {TAB_CONFIG.map((tab, index) => (
        <Tab
          key={index}
          data-index={index}
          label={tab.label}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
          sx={theme => ({
            width: "auto",
            minWidth: { xs: 0, lg: 50 },
            height: 80,
            flexGrow: 1,
            fontSize: { md: '1rem' },
            fontWeight: 600,
            color: 'common.black',
          })}
        />
      ))}
    </Tabs>
  )
}

export default TabList;