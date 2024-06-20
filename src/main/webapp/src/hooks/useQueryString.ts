import {useLocation} from 'react-router-dom';
import {Option} from 'components/Select/useSelect';

/**
 * 쿼리스트링을 읽어 각 형식에 맞게 반환합니다.
 * @example 데모페이지/list 컴포넌트 참고
 */
const useQueryString = () => {
  const location = useLocation();
  const paramObject = new URLSearchParams(location.search);

  const queryParams = new Map();
  for (const key of paramObject.keys()) {
    queryParams.set(key, paramObject.get(key));
  }
  const params = Object.fromEntries(queryParams);

  const get = (key: string) => paramObject.get(key) || '';

  // DatePicker의 initialValue 타입과 동일합니다.
  const initDate = (value: string) => (params[value] ? new Date(params[value]) : null);

  // 매칭되는 value가 없다면 첫번째 옵션을 반환합니다.
  const initSelect = (value: string, options: Option[]) =>
    options.find(v => v.value === String(params[value])) || options[0];

  return {params, get, initDate, initSelect} as const;
};

export default useQueryString;
