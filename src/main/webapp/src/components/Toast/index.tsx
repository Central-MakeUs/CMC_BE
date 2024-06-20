import 'react-toastify/dist/ReactToastify.css';

import {toast, ToastContainer} from 'react-toastify';
import React from 'react';

/**
 * 토스트 알림 컴포넌트입니다.
 * `src/QueryClientProvider.tsx`에 전역으로 사용할수있도록 설정되어 있습니다.
 *
 * @example
 * openToast('알림 내용');
 */
const ToastAlert = () => {
  return (
    <ToastContainer
      position='top-center'
      closeOnClick
      limit={1}
      autoClose={2000}
      closeButton={false}
      pauseOnHover={false}
      hideProgressBar={true}
      pauseOnFocusLoss={false}
      theme='colored'
      toastStyle={{
        minHeight: 0,
        background: '#424957',
        color: '#ffffff',
        boxShadow: 'none',
        margin: '0 auto',
        width: '335px',
        padding: '18px',
        borderRadius: '16px',
      }}
      bodyStyle={{
        padding: 0,
        margin: 0,
        fontFamily: 'Pretendard',
        letterSpacing: '-0.01em',
        fontSize: '14px',
        fontWeight: '500',
      }}
    />
  );
};

function openToast(message: string) {
  return toast(message);
}

export {openToast, ToastAlert};
