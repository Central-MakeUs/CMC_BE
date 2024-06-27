import React from 'react';

const Dashboard = React.lazy(() => import('../pages/dashboard/Dashboard'));

const DemoList = React.lazy(() => import('../pages/demo/List'));
const QRCodeList = React.lazy(() => import('../pages/attendance/QRCodeList'));
const UserList = React.lazy(() => import('../pages/user/UserList'));
const GenerationWeekList = React.lazy(() => import('../pages/generationWeek/GenerationWeekList'));
const DemoDetail = React.lazy(() => import('../pages/demo/Detail'));
const TextEditor = React.lazy(() => import('../pages/demo/EditorPage'));

const routes = [
  {path: '/dashboard', component: Dashboard},
  {path: '/admin-page/dashboard', component: Dashboard},
  {path: '/user/attendance', component: UserList},
  {path: '/generation-week', component: GenerationWeekList},
  {path: '/user/attendance/qrcode', component: QRCodeList},
  {path: '/demo/list', component: DemoList},
  {path: '/demo/editor', component: TextEditor},
  {path: '/demo/detail/:id', component: DemoDetail},
];

export default routes;
