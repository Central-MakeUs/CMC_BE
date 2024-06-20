import React, {Dispatch, InputHTMLAttributes, SetStateAction} from 'react';
import ReactDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {ko} from 'date-fns/esm/locale';
import {CFormInput} from '@coreui/react';

export interface RangeDatePickerProps extends InputHTMLAttributes<HTMLInputElement> {
  placeholder?: string;
  onChangeDateRange: Dispatch<SetStateAction<[Date | null, Date | null]>>;
  startDate: Date | null;
  endDate: Date | null;
  showTime: boolean;
  showMonthYearPicker: boolean;
}
/**
 * 범위날짜를 입력하는 인풋입니다. `useDatePicker`를 통해 사용합니다.
 */
const RangeDatePicker = ({
  placeholder,
  onChangeDateRange,
  startDate,
  endDate,
  showTime,
  showMonthYearPicker,
}: RangeDatePickerProps) => {
  const onChange = ([start, end]: [Date, Date]) => {
    onChangeDateRange([start, end]);
  };
  return (
    <ReactDatePicker
      selected={startDate}
      startDate={startDate}
      endDate={endDate}
      selectsRange
      onChange={onChange}
      dateFormat={showTime ? 'yyyy년 MM월 dd일 hh시 mm분' : 'yyyy년 MM월 dd일'}
      locale={ko}
      placeholderText={placeholder}
      customInput={<CFormInput value={`${String(startDate)} - ${String(endDate)}`} />}
      showTimeInput={showTime}
      showMonthYearPicker={showMonthYearPicker}
    />
  );
};

export default React.memo(RangeDatePicker);
