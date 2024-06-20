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
    const [isExistsFilteredRoute] = useState(
        routes.filter(route =>
            location.pathname.startsWith("/admin-page" + route.path) ||
            location.pathname.startsWith(route.path)
        ).length > 0
    );

    routes.map((route) => {
        console.log(route.path)
    })
    console.log(location.pathname)
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
