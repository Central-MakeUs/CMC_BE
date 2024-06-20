import React, {ReactNode} from 'react';
import {useState} from 'react';
import styled from 'styled-components';
import RangeDatePicker from './RangeDatePicker';
import SingleDatePicker from './SingleDatePicker';

type SingleDate = Date | null;
export type RangeDate = [Date | null, Date | null];
type RenderPickerType = (props: PickerProps) => ReactNode;

type DatePickerReturnType<T> = T extends RangeDate
  ? [RangeDate, RenderPickerType, React.Dispatch<React.SetStateAction<[Date | null, Date | null]>>]
  : [SingleDate, RenderPickerType, React.Dispatch<React.SetStateAction<Date | null>>];

interface PickerProps {
  placeholder: string;
  width?: number;
  showTime?: boolean;
  showMonthYearPicker?: boolean;
}

const isRangeDateGuard = (date: SingleDate | RangeDate): date is RangeDate => {
  return (date as RangeDate)?.length === 2;
};

/**
 *
 * @param initialDate [Date,Date] 형식의 배열을 넘기면 범위날짜 데이트피커 렌더링
 * @example
 *  const [date, renderDatePicker] = useDatePicker(null);
    const [rangeDate, renderDateRangePicker] = useDatePicker([null, null]);
    // ...

    <DatePicker placeholder='날짜를 입력해주세요' />
    <DateRangePicker placeholder='기간을 입력해주세요' width={270} />
    
    // 컴포넌트 형식으로 사용시 rangeDate일때 하루 선택하면 픽커 꺼지는 이슈 있음.
    // render 함수 형식으로 불러와 사용하기.
    {renderDatePicker({placeholder: '기간을 선택해주세요'})}


 *
 * @returns [date 또는 [date,date], renderDatePicker]
 */
const useDatePicker = <T extends SingleDate | RangeDate>(initialDate: T = null as T): DatePickerReturnType<T> => {
  const isRangeDate = isRangeDateGuard(initialDate);

  const [date, selectDate] = useState<Date | null>(isRangeDate ? initialDate[0] : initialDate);
  const [rangeDate, selectRangeDate] = useState<[Date | null, Date | null]>(isRangeDate ? initialDate : [null, null]);

  /**
   * @param {
   *    placeholder?: string;
   *    width?: number;
   *    showTime?: boolean;
   *    showMonthYearPicker?: boolean;
   * }
   * @example
   * <DateRangePicker placeholder='기간을 입력해주세요' width={270} />
   */
  const DatePicker = ({placeholder, width, showTime = false, showMonthYearPicker = false}: PickerProps) => (
    <DatePickerContainer width={width ? width : isRangeDate ? 270 : undefined}>
      {isRangeDate ? (
        <RangeDatePicker
          placeholder={placeholder}
          onChangeDateRange={selectRangeDate}
          startDate={rangeDate[0]}
          endDate={rangeDate[1]}
          showTime={showTime}
          showMonthYearPicker={showMonthYearPicker}
        />
      ) : (
        <SingleDatePicker
          placeholder={placeholder}
          onChangeDate={selectDate}
          selectedDate={date}
          showTime={showTime}
          showMonthYearPicker={showMonthYearPicker}
        />
      )}
    </DatePickerContainer>
  );

  return [
    isRangeDate ? rangeDate : date,
    DatePicker,
    isRangeDate ? selectRangeDate : selectDate,
  ] as DatePickerReturnType<T>;
};

export default useDatePicker;

const DatePickerContainer = styled.div<{width?: number}>`
  & > div {
    width: ${({width}) => (width ? `${width}px` : `auto`)};
  }
`;
