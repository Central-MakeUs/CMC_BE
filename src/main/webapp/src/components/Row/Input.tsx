import {CCol, CFormInput, CFormLabel, CRow} from '@coreui/react';
import {CFormInputProps} from '@coreui/react/dist/components/form/CFormInput';
import React, {ReactNode} from 'react';

export interface InputProps extends CFormInputProps {
  defaultValue?: string | number;
  label: ReactNode;
}

/**
 * @param 목록화면에서 사용하는 label : {인풋창} 형태의 컴포넌트입니다.
 * @example <Row.Input label={} onChange={}/>
 */
const Input = ({label, name, ...props}: InputProps) => {
  return (
    <CRow className='mb-3'>
      <CFormLabel className='col-sm-2 col-form-label' htmlFor={name}>
        <b>{label}</b>
      </CFormLabel>
      <CCol sm={10}>
        <CFormInput name={name} {...props} />
      </CCol>
    </CRow>
  );
};

export default Input;
