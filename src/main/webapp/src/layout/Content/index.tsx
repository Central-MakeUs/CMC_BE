import React, {Suspense, useState} from 'react';
import {Navigate, Route, Routes, useLocation} from 'react-router-dom';

// routes config
import routes from '../../config/routes';

const loading = (
  <div className='pt-3 text-center'>
    <div className='sk-spinner sk-spinner-pulse'/>
  </div>
);

const Content = () => {
  const location = useLocation();
  const [isExistsFilteredRoute] = useState(routes.filter(route => route.path === location.pathname).length > 0);

  console.log(`isExistsFilteredRoute : ${isExistsFilteredRoute}`)
  return (
    <Suspense fallback={loading}>
      {!isExistsFilteredRoute ? (
        <Navigate to='/admin-page/login'/>
      ) : (
        <Routes>
          {routes.map((route, idx) => {
            return route.component && <Route key={idx} path={route.path} element={<route.component/>}/>;
          })}
          <Route path='/admin-page/' element={<Navigate to='/admin-page/dashboard' replace/>}/>
        </Routes>
      )}
    </Suspense>
  );
};

export default React.memo(Content);
