import React, {useEffect} from 'react';
import Sidebar from './Sidebar';
import Header from './Header';
import Footer from './Footer';
import {useNavigate} from 'react-router-dom';
import {clearJwt, clearUser, getJwt} from '../utils/utility';
import {openToast} from '../components/Toast';
import Content from './Content';
import {loginApi} from "../apis/handlers/login";

const DefaultLayout = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!getJwt()) {
      openToast('로그인이 필요합니다.');
      clearJwt();
      navigate(`/admin-page/login`);
      return;
    }

    const fetchData = async () => {
      try {
        await loginApi.postUsersAutoLogin(getJwt()).then((result) => {
          if (!result) {
            clearUser();
            clearJwt();
            navigate(`/admin-page/login`);
          }
        })
      } catch (error) {
        openToast(`${error}가 발생했습니다. 로그인 페이지로 돌아갑니다.`);
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
