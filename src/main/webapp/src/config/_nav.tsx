import React, {ElementType, ReactNode} from 'react';
import {cilAddressBook, cilHome, cilStar} from '@coreui/icons';
import CIcon from '@coreui/icons-react';
import {CNavGroup, CNavItem} from '@coreui/react';

export type Badge = {
  color: string;
  text: string;
};

export type NavItem = {
  component: string | ElementType;
  name: string;
  icon?: ReactNode;
  badge?: Badge;
  to: string;
  items?: NavItem[];
};

// TODO : 아이콘 추가시 아래 링크 참고
// https://coreui.io/react/docs/components/icon/
const _nav = [
  {
    component: CNavItem,
    name: 'CMC Root Page',
    icon: <CIcon icon={cilHome} customClassName='nav-icon'/>,
    to: '/admin-page/dashboard',
  },
  {
    component: CNavGroup,
    name: '동아리 인원 관리',
    icon: <CIcon icon={cilAddressBook} customClassName='nav-icon'/>,
    items: [
      {
        component: CNavItem,
        name: '유저 조회',
        to: '/admin-page/user/attendance',
      },
      {
        component: CNavItem,
        name: 'QR 코드 조회',
        to: '/admin-page/user/attendance/qrcode',
      },
    ],
  },

  {
    component: CNavGroup,
    name: '예제 그룹',
    icon: <CIcon icon={cilStar} customClassName='nav-icon'/>,
    items: [
      {
        component: CNavItem,
        name: '{도메인} 목록 조회',
        to: '/admin-page/demo/list',
      },
      {
        component: CNavItem,
        name: '{도메인} 상세 화면',
        to: '/admin-page/demo/detail/1',
      },
      {
        component: CNavItem,
        name: '텍스트 에디터',
        to: '/admin-page/demo/editor',
      },
    ],
  },
];

export default _nav;
