import React from 'react';

const Dashboard = React.lazy(() => import('../pages/dashboard/Dashboard'));

const DemoList = React.lazy(() => import('../pages/demo/List'));
const UserList = React.lazy(() => import('../pages/user/UserList'));
const DemoDetail = React.lazy(() => import('../pages/demo/Detail'));
const TextEditor = React.lazy(() => import('../pages/demo/EditorPage'));

const routes = [
  {path: '/dashboard', component: Dashboard},
  {path: '/user/attendance', component: UserList},
  {path: '/user/attendance/qrcode', component: UserList},
  {path: '/demo/list', component: DemoList},
  {path: '/demo/editor', component: TextEditor},
  {path: '/demo/detail/:id', component: DemoDetail},
];

export default routes;
