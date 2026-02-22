import React, { useState } from 'react';
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';

import {
  AppBar,
  Avatar,
  Hidden,
  IconButton,
  Toolbar,
  Typography,
} from '@mui/material';

import BagButton from "./BagButton";
// import MobileMenu from "./MobileMenu";
import SearchBar from "./SearchBar";
import SideBar from './SideBar'
import TabList from './TabList';

import { SHOPPERS_PRODUCT_INFO_COOKIE, AUTH_DETAILS_COOKIE } from "../../../constant/cookie";
import { TABS_DATA_API } from "../../../constant/api-route";
import { TABS_API_OBJECT_LEN } from "../../../constant/constant"

// import history from "../../../history";

const NavigationBar = () => {
  const [hamburgerBtnState, setHamburgerBtnState] = React.useState(false);
  const [mobileSearchState, setMobileSearchState] = React.useState(false);
  const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);

  const dispatch = useDispatch();
  const signIn = useSelector(state => state.signIn);
  const googleAuth = useSelector(state => state.googleAuth)
  const tabsData = useSelector(state => state.tabsData)

  let authIcon = null
  let authLabel = null
  const mobileMenuId = 'primary-search-account-menu-mobile';
  const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

  const handleSidebarOpen = () => {
    setHamburgerBtnState(true)
  }

  const handleSidebarClose = () => {
    setHamburgerBtnState(false)
  }

  const handleMobileSearchOpen = () => {
    setMobileSearchState(true)
  }

  const handleMobileSearchClose = () => {
    setMobileSearchState(false)
  }

  return (
    <SearchBar device='mobile' size='medium' handleClose={handleMobileSearchClose} />
    // <>
    //   <SideBar open={hamburgerBtnState} closeHandler={handleSidebarClose} />
    //   <div style={{ paddingBottom: 80 }}>
    //     <AppBar color="default" className={classes.appBarRoot}>
    //       <Toolbar classes={{ root: classes.toolBarRoot }}>
    //         <Grid container alignItems="center">
    //           <Hidden lgUp>
    //             {!mobileSearchState ?
    //               <Grid item>
    //                 <IconButton
    //                   edge="start"
    //                   className={classes.menuButton}
    //                   color="inherit"
    //                   aria-label="open drawer"
    //                   onClick={handleSidebarOpen}>
    //                   <MenuIcon fontSize="large" />
    //                 </IconButton>
    //               </Grid> : null}
    //           </Hidden>

    //           {!mobileSearchState ? <Grid item>
    //             <Link to="/">
    //               <Typography className={classes.title}>
    //                 Shoppers
    //               </Typography>
    //             </Link>
    //           </Grid> : null}

    //           <div className={classes.growHalf} />

    //           <Hidden mdDown>
    //             <Grid item xs={5}>
    //               <TabList />
    //             </Grid>

    //             <div className={classes.growHalf} />
    //           </Hidden>

    //           <Hidden xsDown>
    //             <Grid item container sm={6} md={7} lg={4}>
    //               <SearchBar size="small" />
    //             </Grid>
    //           </Hidden>

    //           <Hidden smUp>
    //             <div className={classes.growHalf} />
    //             <div className={classes.growHalf} />
    //             {renderMobileSearchInputField()}
    //           </Hidden>

    //           <Hidden xsDown>
    //             <div className={classes.growHalf} />

    //             {renderIndependentElem(changeAuthStatusHandler, authIcon, authLabel,
    //               2)}

    //             <div className={classes.growQuarter} />

    //             {renderIndependentElem(changePageToShoppingBagHandler, <BagButton />,
    //               "Bag", 0)}
    //           </Hidden>

    //         </Grid>
    //       </Toolbar>
    //     </AppBar>

    //     <MobileMenu mobileMenuId={mobileMenuId}
    //       authIcon={authIcon}
    //       authLabel={authLabel}
    //       authBtnHandler={changeAuthStatusHandler}
    //       bagBtnHandler={changePageToShoppingBagHandler}
    //       mobileMoreAnchorEl={mobileMoreAnchorEl}
    //       isMobileMenuOpen={isMobileMenuOpen}
    //       handleMobileMenuClose={handleMobileMenuClose}
    //     />
    //   </div>
    // </>
  );
}

export default NavigationBar;