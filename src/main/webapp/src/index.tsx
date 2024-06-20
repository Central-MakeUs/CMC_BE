import 'react-app-polyfill/ie11'; // For IE 11 support
import 'react-app-polyfill/stable';
import 'core-js';
import './scss/style.scss';
import React, {Suspense} from 'react';
import {createRoot} from 'react-dom/client';
import {Provider} from 'react-redux';
import store from 'config/store';
import packageJson from '../package.json';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {QueryClientProvider} from './QueryClientProvider';

console.log(`%cNE(O)RDINARY Project Name : ${packageJson.name}`, 'color:yellow');
console.log(`%cNE(O)RDINARY Project Version : ${packageJson.version}`, 'color:yellow');
console.log(`%cNE(O)RDINARY Project Made by : ${packageJson.author}`, 'color:yellow');

console.log(`
███╗   ██╗███████╗ ██╗ ██████╗ ██╗ ██████╗ ██████╗ ██╗███╗   ██╗ █████╗ ██████╗ ██╗   ██╗
████╗  ██║██╔════╝██╔╝██╔═══██╗╚██╗██╔══██╗██╔══██╗██║████╗  ██║██╔══██╗██╔══██╗╚██╗ ██╔╝
██╔██╗ ██║█████╗  ██║ ██║   ██║ ██║██████╔╝██║  ██║██║██╔██╗ ██║███████║██████╔╝ ╚████╔╝
██║╚██╗██║██╔══╝  ██║ ██║   ██║ ██║██╔══██╗██║  ██║██║██║╚██╗██║██╔══██║██╔══██╗  ╚██╔╝
██║ ╚████║███████╗╚██╗╚██████╔╝██╔╝██║  ██║██████╔╝██║██║ ╚████║██║  ██║██║  ██║   ██║
╚═╝  ╚═══╝╚══════╝ ╚═╝ ╚═════╝ ╚═╝ ╚═╝  ╚═╝╚═════╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝
`);

const loading = <div>화면을 불러오는 중 입니다.</div>;
const DefaultLayout = React.lazy(() => import('./layout/DefaultLayout'));

const Login = React.lazy(() => import('./pages/login/Login'));
const Register = React.lazy(() => import('./pages/register/Register'));
const Page404 = React.lazy(() => import('./pages/page404/Page404'));
const Page500 = React.lazy(() => import('./pages/page500/Page500'));

const container = document.getElementById('root');
// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
const root = createRoot(container!);

root.render(
  <Provider store={store}>
    <BrowserRouter>
      <QueryClientProvider>
        <Suspense fallback={loading}>
          <Routes>
            <Route path='/login' element={<Login/>}/>
            <Route path='/register' element={<Register/>}/>
            <Route path='/404' element={<Page404/>}/>
            <Route path='/500' element={<Page500/>}/>
            <Route path='/*' element={<DefaultLayout/>}/>
            <Route path='/admin-page/login' element={<Login/>}/>
            <Route path='/admin-page/register' element={<Register/>}/>
            <Route path='/admin-page/404' element={<Page404/>}/>
            <Route path='/admin-page/500' element={<Page500/>}/>
            <Route path='/admin-page/*' element={<DefaultLayout/>}/>
          </Routes>
        </Suspense>
      </QueryClientProvider>
    </BrowserRouter>
  </Provider>,
);
