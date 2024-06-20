import {Dispatch, InputHTMLAttributes, SetStateAction} from 'react';
import ReactDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {ko} from 'date-fns/esm/locale';
import React from 'react';
import {CFormInput} from '@coreui/react';

export interface DatePickerProps extends InputHTMLAttributes<HTMLInputElement> {
  placeholder?: string;
  onChangeDate: Dispatch<SetStateAction<Date | null>>;
  selectedDate: Date | null;
  showTime: boolean;
  showMonthYearPicker: boolean;
}

/**
 * 날짜를 입력하는 인풋입니다. `useDatePicker`를 통해 사용합니다.
 */
const SingleDatePicker = ({
  selectedDate,
  placeholder,
  onChangeDate,
  showTime,
  showMonthYearPicker,
}: DatePickerProps) => {
  return (
    <ReactDatePicker
      selected={selectedDate}
      onChange={onChangeDate}
      disabledKeyboardNavigation
      dateFormat={showTime ? 'yyyy년 MM월 dd일 hh시 mm분' : 'yyyy년 MM월 dd일'}
      locale={ko}
      placeholderText={placeholder}
      customInput={<CFormInput value={String(selectedDate)} />}
      showTimeInput={showTime}
      showMonthYearPicker={showMonthYearPicker}
    />
  );
};

export default SingleDatePicker;
