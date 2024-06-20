import React, {useEffect} from 'react';
import Sidebar from './Sidebar';
import Header from './Header';
import Footer from './Footer';
import {useNavigate} from 'react-router-dom';
import {clearJwt, clearUser, getJwt} from '../utils/utility';
import {openToast} from '../components/Toast';
import Content from './Content';

const DefaultLayout = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!getJwt()) {
      openToast('로그인이 필요합니다.');
      clearJwt();
      navigate(`/login`);
      return;
    }

    const fetchData = async () => {
      try {
        // TODO 서버 API 나오면 주석 해제
        // await loginApi.postUsersAutoLogin(getJwt())
      } catch (error) {
        // TODO 서버 http response status 에 맞춰서 '자동 로그인이 만료되었습니다.' 메시지로 변경
        openToast(error);
        clearUser();
        clearJwt();
        navigate(`/admin-page/login`);
      }
    };
    fetchData().then();
  }, [navigate]);

  return (
    <>
      <Sidebar/>
      <div className='wrapper d-flex flex-column min-vh-100 bg-light dark:bg-transparent'>
        <Header/>
        <div className='body flex-grow-1 px-3'>
          <Content/>
        </div>
        <Footer/>
      </div>
    </>
  );
};

export default DefaultLayout;
