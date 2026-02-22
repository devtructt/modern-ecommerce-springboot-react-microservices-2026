
export const MAX_PRODUCTS_PER_PAGE = 16;

export const TAB_CONFIG = [
    { id: "men", title: "MEN", color: "#ee5f73" },
    { id: "women", title: "WOMEN", color: "#fb56c1" },
    { id: "boys", title: "KIDS", color: "#f26a10" },
    { id: "essentials", title: "ESSENTIALS", color: "#0db7af" },
    { id: "homeAndLiving", title: "HOME & LIVING", color: "#f2c210" },
];

export const INITIAL_PAGINATION_STATE = {
    pageNumber: 1,
    maxProducts: MAX_PRODUCTS_PER_PAGE,
    isLoadedFromURL: true
}

export const INITIAL_SORT_STATE = {
    id: 1,
    value: null,
    isLoadedFromURL: true
}

export const INITIAL_SELECTED_FILTER_ATTRIBUTE_STATE = {
    genders: [],
    apparels: [],
    brands: [],
    prices: [],
    oldQuery: null,
    newQuery: null
}

export const INITIAL_SHIPPING_OPTION_STATE = {
    price: "Free",
    submitted: false
}

export const INITIAL_SHIPPING_ADDRESS_STATE = {
    values: null,
    submitted: false
}

export const FILTER_ATTRIBUTES = ["genders", "apparels", "brands", "prices"]
export const SORT_ATTRIBUTE = "sortby"
export const PAGE_ATTRIBUTE = "page"

export const MONTHS = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"];

export const HOME_PAGE_API_OBJECT_LEN = 3;
export const TABS_API_OBJECT_LEN = 6;
