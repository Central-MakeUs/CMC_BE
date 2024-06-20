import React, {PropsWithChildren, ReactNode} from 'react';
import {CCol, CFormLabel, CRow} from '@coreui/react';
import styled from 'styled-components';

export interface CustomRowProps extends PropsWithChildren<React.ComponentProps<'div'>> {
  label: ReactNode;
}

/**
 * @param 목록화면에서 사용하는 label : {element} 형태의 컴포넌트입니다.
 * @example <Row.Textarea label={}>{element}</Row.Textare>
 */
const Custom = ({label, children}: CustomRowProps) => {
  return (
    <CRow className='mb-3'>
      <CFormLabel className='col-sm-2 col-form-label'>
        <b>{label}</b>
      </CFormLabel>
      <StyledCCol sm={10}>{children}</StyledCCol>
    </CRow>
  );
};

export default Custom;

const StyledCCol = styled(CCol)`
  padding-top: calc(0.375rem + 1px);
  padding-bottom: calc(0.375rem + 1px);
`;
