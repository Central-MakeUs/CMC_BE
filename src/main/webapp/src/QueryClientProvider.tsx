import {QueryClient, QueryClientProvider as BaseQueryClientProvider} from '@tanstack/react-query';
import {ReactQueryDevtools} from '@tanstack/react-query-devtools';
import request from 'apis/core';
import {openToast, ToastAlert} from 'components/Toast';

import React from 'react';
import {useNavigate} from 'react-router-dom';
import {clearJwt, clearUser} from './utils/utility';

const queryClient = new QueryClient();

const QueryClientProvider = (props: React.PropsWithChildren<unknown>) => {
  const {children} = props;
  const navigate = useNavigate();

  // 에러 리스폰스의 형식은 실제 api의 응답과 달라질 수 있습니다.
  // 백엔드와의 협의 후 변경해주세요.
  queryClient.setDefaultOptions({
    mutations: {
      retry: false,
      onError: (e: any) => {
        openToast(e.reason ?? e.response.data.reason ?? '네트워크 통신 오류가 발생했습니다. 관리자에게 문의하세요.');
      },
    },
    queries: {
      retry: false,
      refetchOnWindowFocus: false,
      onError: (e: any) => {
        openToast(e.reason ?? e.response.data.reason ?? '네트워크 통신 오류가 발생했습니다. 관리자에게 문의하세요.');
        if (e.status === 401) {
          clearJwt();
          clearUser();
          request.defaults.headers.common.Authorization = ``;
          navigate('/admin-page/login');
        }
      },
    },
  });

  return (
    <BaseQueryClientProvider client={queryClient}>
      {children}
      <ToastAlert/>
      <ReactQueryDevtools initialIsOpen={false}/>
    </BaseQueryClientProvider>
  );
};

export {queryClient, QueryClientProvider};
