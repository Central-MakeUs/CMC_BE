import {CButton, CFormInput} from '@coreui/react';
import {Column} from '@coreui/react/dist/components/table/types';
import DateEasySelect from 'components/DatePicker/DateEasySelect';
import useDatePicker from 'components/DatePicker/useDatePicker';
import {FlexBox} from 'components/FlexBox';
import Row from 'components/Row';
import Section from 'components/Section';
import useSelect, {Option} from 'components/Select/useSelect';
import useSelectSize from 'components/Select/useSelectSize';
import {Spacing} from 'components/Spacing';
import Table from 'components/Table';
import useQueryString from 'hooks/useQueryString';
import {useInput} from 'hooks/useInput';
import useQueryStringEffect from 'hooks/useQueryStringEffect';
import React, {useEffect, useState} from 'react';
import {useQuery} from '@tanstack/react-query';
import {mockApi} from 'apis/mocks';
import {getTableResponseType} from 'utils/getTableResponseType';
import {date} from 'utils/date';
import ModalButton from 'components/ModalButton';
import excel from 'utils/excel';
import {openToast} from 'components/Toast';

// useSelect 관련 컴포넌트 옵션
const SEARCH_OPTIONS: Option[] = [
  {label: '전체', value: null},
  {label: 'ID', value: 'id'},
  {label: '운영자명', value: 'manager'},
  {label: '휴대폰번호', value: 'phone'},
  {label: '부서명', value: 'department'},
  {label: '담당업무', value: 'duty'},
];
const STATUS_OPTIONS: Option[] = [
  {label: '전체', value: null},
  {label: '활성화', value: 'true'},
  {label: '비활성화', value: 'false'},
];
const ORDER_OPTIONS: Option[] = [
  {label: '생성일시 최신순', value: 'createdAtDesc'},
  {label: '생성일시 오래된순', value: 'createdAtAsc'},
  {label: '로그인일시 최신순', value: 'loginedAtDesc'},
  {label: '로그인일시 오래된순', value: 'loginedAtAsc'},
];

const DEMO_COLUMNS: Column[] = [
  {label: 'no', key: 'index'},
  {label: '모달 띄우기', key: 'modal'},
  {label: 'ID(email)', key: 'email'},
  {label: '운영자명', key: 'manager'},
  {label: '휴대폰번호', key: 'phone'},
  {label: '부서명', key: 'department'},
  {label: '담당업무', key: 'duty'},
  {label: '상태', key: 'status'},
  {label: '이미지', key: 'image'},
  {label: '계정 생성일시', key: 'createdAt'},
  {label: '최종 로그인일시', key: 'loginedAt'},
];

const List = () => {
  // 쿼리스트링 정보를 읽어 각 input(검색 조건)을 초기화하는데 필요한 함수들입니다.
  const {get, initDate, initSelect} = useQueryString();

  //useDatePicker([초기값, 초기값]) 형태로 사용합니다.
  const [registerDate, renderResigisterDatePicker, setRegisterDate] = useDatePicker([
    initDate('registerStartDate'),
    initDate('registerEndDate'),
  ]);
  const [loginDate, renderLoginDatePicker, setLoginDate] = useDatePicker([
    initDate('loginStartDate'),
    initDate('loginEndDate'),
  ]);
  //useSelect(옵션 배열, 초기값) 형태로 사용합니다.
  const [search, SearchSelect, setSearchSelect] = useSelect(SEARCH_OPTIONS, initSelect('search', SEARCH_OPTIONS));
  const [status, StatusSelect, setStatusSelect] = useSelect(STATUS_OPTIONS, initSelect('status', STATUS_OPTIONS));
  const [order, OrderSelect] = useSelect(ORDER_OPTIONS, initSelect('order', ORDER_OPTIONS));

  const [searchValue, onChangeSearchValue, setSearchValue] = useInput(get('searchValue'));
  const [page, setPage] = useState(Number(get('page') || 0));
  // useSelectSize hooks에는 서치파라미터 초기화 로직이 내장되어있습니다.
  const [size, SizeSelect] = useSelectSize(() => setPage(0));

  // 열 선택 기능이 있는 테이블의 경우, 선택된 데이터의 key value를 저장합니다.
  const [selected, setSelected] = useState<any[]>([]);

  useEffect(() => {
    console.log(selected);
  }, [selected]);

  // 의존성 배열(두번째인자)에 추가한 값들이 변경될때마다, 첫번째 인자로 넘겨준 객체대로 쿼리스트링에 반영됩니다.
  useQueryStringEffect(
    {
      registerStartDate: registerDate[0]?.toJSON(),
      registerEndDate: registerDate[1]?.toJSON(),
      loginStartDate: loginDate[0]?.toJSON(),
      loginEndDate: loginDate[1]?.toJSON(),
      search,
      status,
      page,
      size,
      order,
      searchValue,
    },
    [registerDate[0], registerDate[1], loginDate[0], loginDate[1], search, status, page, size, order, searchValue],
  );

  /**
   *
   * useQuery의 querykey에 다양한 변수들을 넣어줄 수 있습니다.
   * @example
   * const {data, status: httpStatus} = useQuery(['mock',page,size, status //..외 기타 키들], () =>
   *
   */
  const {
    data,
    status: httpStatus,
    refetch,
  } = useQuery(
    ['mock'],
    () =>
      mockApi.getData({
        //... get request search params..
      }),
    {
      // onSuccess 옵션을 통해 api패칭이 성공했을때 로직을 추가할 수 있습니다.
      onSuccess: data => console.log(`${data.length}`),
    },
  );

  /**
   * DateEasySelect 라디오버튼 선택 초기화를 위한 상태입니다.
   * `resetKey`가 변경되면 해당 컴포넌트 내부 상태가 초기화되도록하는 트리거입니다.
   * 더 좋은 구조가 있다면 코드를 수정해주셔도 좋아요.
   *
   * 'components/DatePicker/DateEasySelect' 참고.
   */
  const [dateEasySelectResetKey, setDateEasySelectResetKey] = useState(0);
  const resetSearchConditions = () => {
    setRegisterDate([null, null]);
    setLoginDate([null, null]);
    setSearchSelect(SEARCH_OPTIONS[0]);
    setStatusSelect(STATUS_OPTIONS[0]);
    setSearchValue('');
    setDateEasySelectResetKey(prev => prev + 1);
  };

  /**
   * 엑셀 다운로드 & 선택 옵션 이용 예시입니다.
   * @param select 선택 다운로드 여부
   */
  const handleExportExcel = async (select?: boolean) => {
    try {
      const data = await mockApi.getData({isExportExcel: true});
      if (select) {
        const selectedData = data.filter(v => selected.includes(v.index));
        data && excel.export('선택 엑셀 다운로드 예시', selectedData);
      } else {
        data && excel.export('엑셀 다운로드 예시', data);
      }
    } catch (e) {
      openToast('오류가 발생했어요');
    }
  };

  return (
    <>
      <Section
        body={
          <>
            <Row.Custom label={'계정 생성기간'}>
              <FlexBox gap={16} justify={'flex-start'}>
                <DateEasySelect onChange={setRegisterDate} name={'registerDate'} resetKey={dateEasySelectResetKey} />
                {renderResigisterDatePicker({placeholder: '기간을 선택해주세요'})}
              </FlexBox>
            </Row.Custom>
            <Row.Custom label={'로그인 기간'}>
              <FlexBox gap={16} justify={'flex-start'}>
                <DateEasySelect onChange={setLoginDate} name={'loginDate'} resetKey={dateEasySelectResetKey} />
                {renderLoginDatePicker({placeholder: '기간을 선택해주세요'})}
              </FlexBox>
            </Row.Custom>
            <Row.Custom label={'검색어'}>
              <FlexBox gap={10}>
                <SearchSelect width={130} />
                <CFormInput
                  placeholder='선택 항목의 검색어를 입력해주세요'
                  value={searchValue}
                  onChange={onChangeSearchValue}
                />
              </FlexBox>
            </Row.Custom>
            <Row.Custom label={'상태'}>
              <StatusSelect width={130} />
            </Row.Custom>
          </>
        }
        footer={
          <FlexBox gap={10}>
            <CButton onClick={() => refetch()}>검색</CButton>
            <CButton color='dark' onClick={resetSearchConditions}>
              초기화
            </CButton>
          </FlexBox>
        }
      />
      <Section
        body={
          <>
            <FlexBox justify={'flex-end'} gap={6}>
              <ModalButton
                title='엑셀 다운로드 데모'
                description={'엑셀을 다운로드하시겠습니까? 네트워크 환경에따라 다소 시간이 걸릴 수 있습니다.'}
                onConfirm={() => handleExportExcel(false)}
              >
                전체 엑셀 다운로드
              </ModalButton>
              <ModalButton
                title='선택 엑셀 다운로드 데모'
                description={'엑셀을 다운로드하시겠습니까? 네트워크 환경에따라 다소 시간이 걸릴 수 있습니다.'}
                onConfirm={() => handleExportExcel(true)}
              >
                선택 엑셀 다운로드
              </ModalButton>
              <OrderSelect />
              <SizeSelect />
            </FlexBox>
            <Spacing size={8} />
            <Table
              column={DEMO_COLUMNS}
              paginationState={[page, setPage]}
              size={size}
              data={getTableResponseType({src: data, size, page})}
              status={httpStatus}
              renderColumnData={{
                createdAt: data => date.formatDate(data.createdAt),
                loginedAt: data => `${date.formatDate(data.loginedAt)} ${date.formatTime(data.loginedAt)}`,
                status: data => (data.status ? '활성화' : '비활성화'),
                image: data => <img src={data.image} style={{width: '100px'}} alt='exampleImage' />,
                modal: data => (
                  <ModalButton
                    title='모달 열기 데모'
                    description={`${data.index}번 데이터`}
                    onConfirm={() => console.log('확인 버튼 클릭시 실행')}
                  >
                    열기
                  </ModalButton>
                ),
              }}
              selectableKey='index'
              updateSelection={setSelected}
              //onRowClick={data => navigate(`/demo/detail/${data.index}`)}
            />
          </>
        }
      />
    </>
  );
};

export default List;
