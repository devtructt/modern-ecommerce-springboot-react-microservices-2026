import React, { useRef } from 'react'
import { Tabs, Tab } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';

import { handleTabHoverEvent } from '../../../slice/event/tab-hover-event-slice';
import { TAB_CONFIG } from '../../../constant/constant';

const TabList = () => {
  const tabsRef = useRef(null);
  const dispatch = useDispatch();
  let { tabIndex } = useSelector(state => state.tabHoverEvent);
  const indicatorColor = tabIndex === null ? 'transparent' : TAB_CONFIG[tabIndex]?.color || 'transparent';

  const handleMouseEnter = event => {
    const tabIndex = parseInt(event.currentTarget.dataset.index, 10);
    dispatch(handleTabHoverEvent({ tabIndex: tabIndex, hover: true }));
  }

  const handleMouseLeave = event => {
    const tabsRefRect = tabsRef.current?.getBoundingClientRect();
    if (!tabsRefRect) return;
    const { clientX, clientY } = event;
    if (clientX < tabsRefRect.left || clientX > tabsRefRect.right || clientY < tabsRefRect.top) {
      dispatch(handleTabHoverEvent({ tabIndex: null, hover: false }));
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
            backgroundColor: indicatorColor,
          }
        }
      }}
    >
      {TAB_CONFIG.map((tab, index) => (
        <Tab
          key={index}
          data-index={index}
          label={tab.title}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
          sx={theme => ({
            height: '80px',
            flexGrow: 1,
            '&.Mui-selected': {
              color: 'black',
              fontWeight: '600',
            }
          })}
        />
      ))}
    </Tabs>
  )
}

export default TabList;