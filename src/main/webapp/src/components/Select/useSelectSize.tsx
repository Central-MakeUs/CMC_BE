import React from 'react';
import useSelect from './useSelect';
import {Option} from './useSelect';
import useQueryString from 'hooks/useQueryString';

const SIZE_OPTIONS: Option[] = [
  {value: 10, label: '10'},
  {value: 20, label: '20'},
  {value: 30, label: '30'},
  {value: 50, label: '50'},
  {value: 100, label: '100'},
];

/**
 * 목록 화면에서 한 페이지당 보여줄 데이터의 사이즈를 선택할때 사용합니다.
 * 쿼리스트링에 있는 'size' 파라미터를 반영하는 로직이 있습니다.
 *
 * @param initPage 사이즈를 변경할때 페이지 인덱스를 0으로 돌려주어야 합니다 (데모 페이지 참고)
 * @example [size, SizeSelect] = useSelectSize(()=>setPage(0));
 *
 * @return [size, SizeSelect, setSize]
 * `useSelect`와 다르게 이 훅은 (옵션 객체가 아닌) 옵션의 value를 반환합니다.
 */
const useSelectSize = (initPage?: () => void) => {
  const {get} = useQueryString();
  const [size, renderSelect, setSize] = useSelect(
    SIZE_OPTIONS,
    get('size') ? SIZE_OPTIONS.find(v => v.value === Number(get('size'))) ?? SIZE_OPTIONS[0] : SIZE_OPTIONS[0],
    initPage,
  );
  const SizeSelect = () => <>{renderSelect({width: 90})}</>;

  return [size as number, SizeSelect, setSize] as const;
};

export default useSelectSize;
