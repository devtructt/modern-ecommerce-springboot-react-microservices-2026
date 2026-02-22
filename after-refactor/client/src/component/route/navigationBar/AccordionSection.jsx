import React from 'react';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Divider,
  Grid,
  Typography,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import { TAB_CONFIG } from '../../../constant/constant';
import { PRODUCTS_ROUTE } from '../../../constant/react-route';

const TOP_BRANDS_TITLE = 'Top Brands';
const TOP_CATEGORIES_TITLE = 'Top Categories';

const HEIGHT = '48px';

const ItemLink = ({ item, queryParam }) => {
  return (
    <Grid item>
      <Link to={`${PRODUCTS_ROUTE}?q=${queryParam}=${item.id}`} style={{ textDecoration: 'none' }}>
        <Typography
          sx={theme => ({
            fontSize: theme.typography.pxToRem(14),
            color: theme.palette.text.primary,
          })}
        >
          {item.value}
        </Typography>
        <Divider />
      </Link>
    </Grid>
  );
};

const NestedAccordion = ({ title, items, queryParam }) => {
  return (
    <Accordion square elevation={0}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        slotProps={{
          root: {
            sx: {
              minHeight: HEIGHT,
              '&.Mui-expanded': {
                height: HEIGHT,
                minHeight: HEIGHT,
                margin: 0,
              },
            },
          },
        }}
      >
        <Typography sx={theme => ({ fontSize: theme.typography.pxToRem(14) })}>
          {title}
        </Typography>
      </AccordionSummary>
      <AccordionDetails>
        <Grid container direction='column' spacing={3} sx={{ paddingLeft: '2rem' }}>
          {items.map((item) => (
            <ItemLink key={item.id} item={item} queryParam={queryParam} />
          ))}
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default function AccordionSection() {
  const tabsData = useSelector((state) => state.tabsDataReducer?.data);

  return TAB_CONFIG.map(tab => {
    const { brands = [], categories = [] } = tabsData?.[tab.id] || {};

    return (
      <Accordion key={tab.id}
        square
        elevation={0}
        sx={{
          '&:before': { display: 'none' },
        }}
      >
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          slotProps={{
            root: {
              sx: {
                minHeight: HEIGHT,
                '&.Mui-expanded': {
                  height: HEIGHT,
                  minHeight: HEIGHT,
                  margin: 0,
                },
              },
            },
          }}
        >
          <Typography sx={(theme) => ({ fontSize: theme.typography.pxToRem(14) })}>
            {tab.title}
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Grid container direction='column'>
            <Grid item>
              <NestedAccordion
                title={TOP_BRANDS_TITLE}
                // items={brands}
                items={[{ id: 1, value: 'A' }, { id: 2, value: 'B' }, { id: 3, value: 'C' }]}
                queryParam='brands'
              />
            </Grid>
            <Grid item>
              <NestedAccordion
                title={TOP_CATEGORIES_TITLE}
                // items={categories}
                items={[{ id: 1, value: 'A' }, { id: 2, value: 'B' }, { id: 3, value: 'C' }]}
                queryParam='categories'
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>
    );
  });
}