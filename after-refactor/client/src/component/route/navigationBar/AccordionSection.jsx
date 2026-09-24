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

const TOP_BRANDS_LABEL = 'Top Brands';
const TOP_CATEGORIES_LABEL = 'Top Categories';
const ACCORDION_HEIGHT = '48px';

function ItemLink({ item, queryParam }) {
  return (
    <Grid>
      <Link to={`${PRODUCTS_ROUTE}?q=${queryParam}=${item.id}`} style={{ textDecoration: 'none' }}>
        <Typography
          sx={(theme) => ({
            fontSize: theme.typography.pxToRem(16),
            fontWeight: theme.typography.fontWeightLight,
            color: theme.palette.text.primary,
          })}
        >
          {item.value}
        </Typography>
        <Divider />
      </Link>
    </Grid>
  );
}

function NestedAccordion({ label, items, queryParam }) {
  return (
    <Accordion square elevation={0}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        sx={{
          minHeight: ACCORDION_HEIGHT,
          height: ACCORDION_HEIGHT,
          width: {
            xs: '244',
            sm: '364',
          },
          '&.Mui-expanded': {
            margin: 0,
            minHeight: ACCORDION_HEIGHT,
            height: ACCORDION_HEIGHT,
            alignItems: "center"
          }
        }}
        slotProps={{
          content: {
            sx: {
              margin: "5px 0",
              padding: "0 15px"
            }
          }
        }}
      >
        <Typography
          sx={(theme) => ({
            fontSize: theme.typography.pxToRem(16),
            fontWeight: theme.typography.fontWeightRegular
          })}
        >
          {label}
        </Typography>
      </AccordionSummary>
      <AccordionDetails sx={{ pt: 0 }}>
        <Grid container direction='column' spacing={3} sx={{ paddingLeft: '2rem' }}>
          {items.map((item) => (
            <ItemLink key={item.id} item={item} queryParam={queryParam} />
          ))}
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
}

function AccordionSection() {
  const tabsData = useSelector(state => state.tabsData?.data);

  return TAB_CONFIG.map(tab => {
    const { brands = [], categories = [] } = tabsData?.[tab.id] || {};

    return (
      <Accordion key={tab.id} square elevation={0} sx={{ width: '100%' }}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          sx={{
            minHeight: ACCORDION_HEIGHT,
            height: ACCORDION_HEIGHT,
            '&.Mui-expanded': {
              margin: 0,
              minHeight: ACCORDION_HEIGHT,
              height: ACCORDION_HEIGHT,
              alignItems: "center"
            }
          }}
          slotProps={{
            content: {
              sx: {
                margin: "5px 0",
                padding: "0 15px"
              }
            }
          }}
        >
          <Typography
            sx={(theme) => ({
              fontSize: theme.typography.pxToRem(16),
              fontWeight: theme.typography.fontWeightMedium
            })}
          >
            {tab.label}
          </Typography>
        </AccordionSummary>
        <AccordionDetails sx={{ pt: 0 }}>
          <Grid container direction='column'>
            <Grid>
              <NestedAccordion
                label={TOP_BRANDS_LABEL}
                // items={brands}
                items={[{ id: 1, value: 'A' }, { id: 2, value: 'B' }, { id: 3, value: 'C' }]}
                queryParam='brands'
              />
            </Grid>
            <Grid>
              <NestedAccordion
                label={TOP_CATEGORIES_LABEL}
                // items={categories}
                items={[{ id: 1, value: 'A' }, { id: 2, value: 'B' }, { id: 3, value: 'C' }]}
                queryParam='categories'
              />
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion >
    );
  });
}

export default React.memo(AccordionSection);
