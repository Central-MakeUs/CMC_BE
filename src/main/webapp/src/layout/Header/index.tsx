import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import {useDispatch} from 'react-redux';
import {useTypedSelector} from 'config/store';
import {
  CButton,
  CContainer,
  CHeader,
  CHeaderNav,
  CHeaderToggler,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
} from '@coreui/react';
import CIcon from '@coreui/icons-react';
import {cilHamburgerMenu} from '@coreui/icons';

const Header = () => {
  const dispatch = useDispatch();
  const sidebarShow = useTypedSelector(state => state.sidebarShow);

  const [visible, setVisible] = useState(false);

  return (
    <CHeader position='sticky' className='mb-4'>
      <CContainer fluid>
        <CHeaderToggler className='ps-1' onClick={() => dispatch({type: 'set', sidebarShow: !sidebarShow})}>
          <CIcon icon={cilHamburgerMenu} size='lg'/>
        </CHeaderToggler>
        <CHeaderNav className='px-3'>
          <CButton onClick={() => setVisible(!visible)}>로그아웃</CButton>
          <CModal visible={visible} onClose={() => setVisible(false)}>
            <CModalHeader>
              <CModalTitle>로그아웃</CModalTitle>
            </CModalHeader>
            <CModalBody>정말로 로그아웃 하시겠습니까?</CModalBody>
            <CModalFooter>
              <CButton color='secondary' onClick={() => setVisible(false)}>
                취소
              </CButton>
              <Link to='/login'>
                <CButton color='primary' onClick={() => window.localStorage.clear()}>
                  확인
                </CButton>
              </Link>
            </CModalFooter>
          </CModal>
        </CHeaderNav>
      </CContainer>
    </CHeader>
  );
};

export default Header;
