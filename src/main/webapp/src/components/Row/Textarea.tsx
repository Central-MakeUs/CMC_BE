import {CCol, CFormLabel, CFormTextarea, CRow} from '@coreui/react';
import {CFormTextareaProps} from '@coreui/react/dist/components/form/CFormTextarea';

import React from 'react';
import styled from 'styled-components';

export interface InputProps extends CFormTextareaProps {
  defaultValue?: string | number;
  label: string;
}

/**
 * @param 목록화면에서 사용하는 label : {textarea} 형태의 컴포넌트입니다.
 * @example <Row.Textarea label={} onChange={}/>
 */
const Textarea = ({defaultValue, label, value, onChange, name, ...props}: InputProps) => {
  return (
    <CRow className='mb-3'>
      <CFormLabel className='col-sm-2 col-form-label' htmlFor={name}>
        <b>{label}</b>
      </CFormLabel>
      <CCol sm={10}>
        <StyledCFormTextarea value={value} onChange={onChange} name={name} {...props} />
      </CCol>
    </CRow>
  );
};

export default Textarea;

const StyledCFormTextarea = styled(CFormTextarea)`
  &::placeholder {
    font-size: 14px;
  }
`;
