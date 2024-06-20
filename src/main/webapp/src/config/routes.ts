import React from 'react';

const Dashboard = React.lazy(() => import('../pages/dashboard/Dashboard'));

const DemoList = React.lazy(() => import('../pages/demo/List'));
const UserAttendance = React.lazy(() => import('../pages/user/List'));
const DemoDetail = React.lazy(() => import('../pages/demo/Detail'));
const TextEditor = React.lazy(() => import('../pages/demo/EditorPage'));

const routes = [
  // {path: '', element: Dashboard},
  {path: '/admin-page/dashboard', component: Dashboard},

  {path: '/user/attendance', component: UserAttendance},
  {path: '/demo/list', component: DemoList},
  {path: '/demo/editor', component: TextEditor},
  {path: '/demo/detail/:id', component: DemoDetail},
];

export default routes;
