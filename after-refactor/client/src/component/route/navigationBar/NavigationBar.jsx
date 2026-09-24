import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from 'react-router-dom';
import Cookies from "js-cookie";

import {
  AppBar,
  Toolbar,
  Grid,
  IconButton,
  Typography,
  Avatar,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import MenuIcon from "@mui/icons-material/Menu";
import MoreVertIcon from "@mui/icons-material/MoreVert";

import TabList from "./TabList";
import SearchBar from "./SearchBar";
import SideBar from "./SideBar";
import MobileMenu from "./MobileMenu";
import BagButton from "./BagButton";

import { signOut } from '../../../slice/signInSlice';
import { signOutUsingOAuth } from '../../../slice/googleAuthSlice';

import { useGetTabsDataQuery } from '../../../api/tabsApi';

import {
  getDataViaAPI,
  setAuthDetailsFromCookie,
  setDefaultSearchSuggestions,
} from "../../../actions";
import { ADD_TO_CART, LOAD_TABS_DATA, SET_GOOGLE_AUTH } from "../../../actions/types";
import {
  SHOPPERS_PRODUCT_INFO_COOKIE,
  AUTH_DETAILS_COOKIE,
} from "../../../constants/cookies";
import { TABS_DATA_API } from "../../../constant/apiRoute";
import { TABS_API_OBJECT_LEN } from "../../../constant/common";

function NavBar({ errorHandler }) {
  const signIn = useSelector(state => state.signIn);
  const googleAuth = useSelector(state => state.googleAuth);
  const tabsData = useGetTabsDataQuery();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [isMobileSearchOpen, setIsMobileSearchOpen] = useState(false);
  const [isHamburgerOpen, setIsHamburgerOpen] = useState(false);
  const [mobileMoreAnchorElement, setMobileMoreAnchorElement] = useState(null);

  const handleMobileSearchOpen = () => setIsMobileSearchOpen(true);
  const handleMobileSearchClose = () => setIsMobileSearchOpen(false);
  const handleSidebarOpen = () => setIsHamburgerOpen(true);
  const handleSidebarClose = () => setIsHamburgerOpen(false);
  const handleMobileMenuOpen = (event) => setMobileMoreAnchorElement(event.currentTarget);
  const handleMobileMenuClose = () => setMobileMoreAnchorElement(null);

  const { icon, label } = getAuthDisplay();

  const getAuthDisplay = () => {
    if (signIn.isSignedIn || googleAuth.isSignedIn) {
      const displayName = signIn.firstName || googleAuth.firstName || "Guest";
      return {
        icon: (
          <Avatar
            sx={{
              width: 20,
              height: 20,
              bgcolor: "orange",
              filter: "saturate(5)",
            }}
          >
            {displayName.charAt(0).toUpperCase()}
          </Avatar>
        ),
        label: "Sign Out",
      };
    }
    return {
      icon: <AccountCircleIcon />,
      label: "Sign In",
    };
  };

  const handleSignOut = () => {
    if (googleAuth.isSignedIn) {
      dispatch(signOutUsingOAuth(googleAuth.oAuth));
    } else if (signIn.isSignedIn && signIn.tokenId) {
      dispatch(signOut());
    } else {
      navigate("/signin");
    }
    handleMobileMenuClose();
  };

  const handleGoToShoppingBag = () => {
    navigate("/shopping-bag");
    handleMobileMenuClose();
  };

  const renderIndependentItem = (onClick, icon, label) => (
    <Grid>
      <Grid
        container
        direction="column"
        alignItems="center"
        onClick={onClick}
        sx={{ cursor: "pointer" }}
      >
        <Grid
          sx={{
            height: 21,
            width: 21,
            pt: 0
          }}>
          {icon}
        </Grid>
        <Grid
          sx={{
            color: "common.black",
            fontSize: "0.8rem",
            fontWeight: "bold",
          }}
        >
          {label}
        </Grid>
      </Grid>
    </Grid>
  );

  if (tabsData.isLoading) return null;
  if (tabsData.data && Object.keys(tabsData.data).length !== TABS_API_OBJECT_LEN) {
    return <BadRequest />;
  }
  if (tabsData.statusCode) {
    return <HTTPError statusCode={tabsData.statusCode} />;
  }

  useEffect(() => {
    // Load cart from cookie
    const savedProducts = Cookies.get(SHOPPERS_PRODUCT_INFO_COOKIE);
    if (savedProducts) {
      const parsed = JSON.parse(savedProducts);
      let totalQuantity = 0;
      Object.values(parsed.productQty || {}).forEach((qty) => {
        totalQuantity += parseInt(qty, 10);
      });

      dispatch({
        type: ADD_TO_CART,
        payload: { ...parsed, totalQuantity },
      });
    }

    // Load auth from cookie
    if (signIn.isSignedIn === null) {
      const savedAuth = Cookies.get(AUTH_DETAILS_COOKIE);
      if (savedAuth) {
        dispatch(setAuthDetailsFromCookie(JSON.parse(savedAuth)));
      }
    }

    // Load tabs
    if (!tabsData.data) {
      dispatch(getDataViaAPI(LOAD_TABS_DATA, TABS_DATA_API, null, false));
    }

    dispatch(setDefaultSearchSuggestions());

    // Google OAuth
    if (!googleAuth.oAuth) {
      window.gapi?.load("client:auth2", () => {
        window.gapi.client
          .init({
            clientId: process.env.REACT_APP_GOOGLE_AUTH_CLIENT_ID,
            scope: "profile",
          })
          .then(() => {
            const oAuth = window.gapi.auth2.getoAuth();
            dispatch({
              type: SET_GOOGLE_AUTH,
              payload: {
                firstName: oAuth.currentUser.get().getBasicProfile()?.getGivenName() || null,
                oAuth: oAuth,
              },
            });
          });
      });
    }
  }, [dispatch, signIn.isSignedIn, googleAuth.oAuth, tabsData.data]);

  return (
    <>
      <SideBar open={isHamburgerOpen} closeHandler={handleSidebarClose} />

      <div style={{ paddingBottom: 80 }}>
        {/* <AppBar color="default" sx={{ height: 80, boxShadow: "none !important" }}> */}
        <AppBar color="default" sx={{ boxShadow: "none" }}>
          <Toolbar sx={{ minHeight: 80 }}>
            <Grid container alignItems="center">
              {!isMobileSearchOpen && (
                <Grid sx={{ display: { lg: "none" } }}>
                  <IconButton
                    edge="start"
                    color="inherit"
                    onClick={handleSidebarOpen}
                    sx={{ mr: 2 }}
                  >
                    <MenuIcon fontSize="large" />
                  </IconButton>
                </Grid>
              )}

              {!isMobileSearchOpen && (
                <Grid>
                  <Link to="/">
                    <Typography
                      sx={{
                        flexGrow: 1,
                        color: 'text.primary',
                        fontSize: { xs: "1.8rem", sm: "2.3rem" },
                        fontWeight: 700,
                        pb: { xs: 0, sm: 0.5 },
                      }}
                    >
                      Shoppers
                    </Typography>
                  </Link>
                </Grid>
              )}

              <Grid item sx={{
                flexGrow: 0.5
              }}
              />

              <Grid sx={{
                flexGrow: 5,
                display: { xs: "none", md: "block" }
              }}
              >
                <TabList />
              </Grid>

              <Grid
                sx={{
                  flexGrow: 0.5,
                  display: { xs: "none", md: "block" }
                }}
              />

              <Grid container
                sx={{
                  flexGrow: { sm: 6, md: 7, lg: 4 },
                  display: { xs: "none", sm: "flex" }
                }}
              >
                <SearchBar size="small" />
              </Grid>

              <Grid
                sx={{
                  flexGrow: 0.5,
                  display: { sm: "none" }
                }}
              />

              <Grid
                sx={{
                  flexGrow: 0.5,
                  display: { sm: "none" }
                }}
              />

              {isMobileSearchOpen ? (
                <Grid>
                  sx={{
                    display: { sm: "none" }
                  }}
                  <SearchBar size="medium" device="mobile" handleClose={handleMobileSearchClose} />
                </Grid>
              ) : (
                <>
                  <Grid
                    sx={{
                      display: { sm: "none" }
                    }}
                  >
                    <IconButton edge="end" onClick={handleMobileSearchOpen}>
                      <SearchIcon fontSize="large" />
                    </IconButton>
                  </Grid>

                  <Grid
                    sx={{
                      display: { sm: "none" }
                    }}
                  >
                    <IconButton edge="end" onClick={handleMobileMenuOpen}>
                      <MoreVertIcon fontSize="large" />
                    </IconButton>
                  </Grid>
                </>
              )}

              <Grid
                sx={{
                  flexGrow: 0.5,
                  display: { xs: "none", sm: "block" }
                }}
              />

              <Grid
                item
                sx={{ display: { xs: "none", sm: "block" } }}
              >
                {renderIndependentItem(handleSignOut, authIcon, authLabel, 2)}
              </Grid>

              <Grid
                sx={{
                  flexGrow: 0.25,
                  display: { xs: "none", sm: "block" }
                }}
              />

              <Grid
                item
                sx={{ display: { xs: "none", sm: "block" } }}
              >
                {renderIndependentItem(handleGoToShoppingBag, <BagButton />, "Bag")}
              </Grid>
            </Grid>
          </Toolbar>
        </AppBar>

        <MobileMenu
          isMobileMenuOpen={isMobileMenuOpen}
          mobileMoreAnchorEl={mobileMoreAnchorElement}
          onClose={handleMobileMenuClose}
          onAuthButtonClick={handleSignOut}
          onBagButtonClick={handleGoToShoppingBag}
          authIcon={authIcon}
          authLabel={authLabel}
        />
      </div>
    </>
  );
};

export default React.memo(NavBar);
