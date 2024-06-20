import {CButton, CFormCheck} from '@coreui/react';
import {ButtonObject} from '@coreui/react/dist/components/form/CFormCheck';
import {FlexBox} from 'components/FlexBox';
import dayjs from 'dayjs';
import React, {useEffect, useState} from 'react';

export interface DateEasySelectProps {
  onChange: (value: [Date | null, Date | null]) => void;
  name: string;
  resetKey?: number;
}

/**
 * 데모 페이지의 기간 빠른 선택 라디오 버튼을 위한 컴포넌트입니다.
 * @prop {} onChange 선택값이 바뀔때 실행될 핸들러 함수
 * @prop {} name input groupd의 name
 * @prop {} resetKey 컴포넌트 외부에서 컴포넌트의 선택값을 초기화하기 위해 사용하는 키입니다. 키 값이 변경되면 선택이 초기화됩니다.
 * @example
 * <FlexBox gap={16} justify={'flex-start'}>
     <DateEasySelect onChange={setLoginDate} name={'loginDate'} resetKey={dateEasySelectResetKey} />
     <LoginDatePicker placeholder='기간을 선택해주세요' />
   </FlexBox>
 */
const DateEasySelect = ({onChange, name, resetKey}: DateEasySelectProps) => {
  const [select, setSelect] = useState<number | undefined>();
  const radioButtonStyle = (index: number): ButtonObject =>
    select === index ? {color: 'primary'} : {color: 'secondary', variant: 'outline'};

  const currentDate = dayjs();

  const today = currentDate.toDate();
  const oneWeekAgo = currentDate.subtract(1, 'week').toDate();
  const oneMonthAgo = currentDate.subtract(1, 'month').toDate();
  const threeMonthsAgo = currentDate.subtract(3, 'month').toDate();

  useEffect(() => {
    if (resetKey !== 0) {
      setSelect(undefined);
    }
  }, [resetKey]);

  return (
    <FlexBox justify={'flex-start'} gap={6}>
      <CFormCheck
        onChange={() => {
          setSelect(1);
          onChange([today, today]);
        }}
        button={radioButtonStyle(1)}
        type='radio'
        name={`${name}-rangeDate`}
        id={`${name}-today`}
        label='오늘'
      />
      <CFormCheck
        onChange={() => {
          setSelect(2);
          onChange([oneWeekAgo, today]);
        }}
        button={radioButtonStyle(2)}
        type='radio'
        name={`${name}-rangeDate`}
        id={`${name}-week`}
        label='1주일'
      />
      <CFormCheck
        onChange={() => {
          setSelect(3);
          onChange([oneMonthAgo, today]);
        }}
        button={radioButtonStyle(3)}
        type='radio'
        name={`${name}-rangeDate`}
        id={`${name}-month`}
        label='1개월'
      />
      <CFormCheck
        onChange={() => {
          setSelect(4);
          onChange([threeMonthsAgo, today]);
        }}
        button={radioButtonStyle(4)}
        type='radio'
        name={`${name}-rangeDate`}
        id={`${name}-quarter`}
        label='3개월'
      />
      <CButton
        color='light'
        onClick={() => {
          onChange([null, null]);
          setSelect(undefined);
        }}
      >
        초기화
      </CButton>
    </FlexBox>
  );
};

export default DateEasySelect;
