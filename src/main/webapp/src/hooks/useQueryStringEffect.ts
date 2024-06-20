import {useEffect} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';

/**
 * 의존성 배열의 변경을 감지해 쿼리스트링을 업데이트해줍니다.
 * @example 더미페이지/list 컴포넌트 참고
 *
 * @param params 쿼리스트링에 반영할 객체
 * @param deps useEffect 의존성 배열
 */
const useQueryStringEffect = (params: object, deps: any[]) => {
  const location = useLocation();
  const navigate = useNavigate();
  const searchParams = new URLSearchParams(location.search);

  const queryParams = new Map();
  for (const key of searchParams.keys()) {
    queryParams.set(key, searchParams.get(key));
  }

  const set = (args: object) => {
    const params = Object.entries(args)
      .filter(([, value]) => value !== undefined && value !== null && value !== '')
      .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
      .join('&');
    return `?${params}`;
  };

  useEffect(() => {
    console.log(params);
    navigate(set(params));
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [...deps]);
};

export default useQueryStringEffect;
