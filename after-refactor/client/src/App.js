import React, { useState, lazy, Suspense } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import NavigationBar from './component/route/navigationBar/NavigationBar';
// import TabPanelList from './component/route/navigationBar/TabPanelList';

// const Home = lazy(() => import('./component/route/home/Home'));
// const SignIn = lazy(() => import('./component/route/signin/SignIn'));
// const SignUp = lazy(() => import('./component/route/signup/SignUp'));
// const Product = lazy(() => import('./component/route/product/Product'));
// const ProductDetail = lazy(() => import('./component/route/detail/ProductDetail'));
// const Checkout = lazy(() => import('./component/route/checkout/Checkout'));
// const ShoppingBag = lazy(() => import('./component/route/ShoppingBag'));
// const SuccessPayment = lazy(() => import('./component/route/SuccessPayment'));
// const CancelPayment = lazy(() => import('./component/route/CancelPayment'));
// const BadRequest = lazy(() => import('./ui/error/BadRequest'));

import { useTheme } from '@mui/material/styles';

const App = () => {
  const [serverError, setServerError] = useState(false);

  const handleServerError = () => setServerError(true);

  if (serverError) {
    return (
      <>
        <NavigationBar errorHandler={handleServerError} />
        {/* <TabPanelList /> */}
      </>
    );
  }

  return (
    <BrowserRouter>
      <NavigationBar errorHandler={handleServerError} />
      {/* <TabPanelList />
      {serverError ? null : (
        <Suspense fallback={<div>Loading...</div>}>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path="/signin" element={<SignIn />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/shopping-bag" element={<ShoppingBag />} />
            <Route path="/checkout" element={<Checkout />} />
            <Route path="/products/:id" element={<ProductDetail />} />
            <Route path="/products" element={<Product />} />
            <Route path="/checkout/success-payment/:id" element={<SuccessPayment />} />
            <Route path="/checkout/cancel-payment/:id" element={<CancelPayment />} />
            <Route path="*" element={<BadRequest />} />
          </Routes>
        </Suspense>
      )} */}
    </BrowserRouter>
  )
}

export default App