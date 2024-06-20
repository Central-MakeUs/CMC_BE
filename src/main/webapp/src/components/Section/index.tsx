import {CCard, CCardBody, CCardFooter, CCardHeader, CCol, CRow} from '@coreui/react';
import React, {ReactNode} from 'react';
import styled from 'styled-components';

export interface SectionProps {
  header?: string | ReactNode;
  body?: string | ReactNode;
  footer?: string | ReactNode;
}

/**
 * coreUI 카드뷰 UI
 * @prop {} header,
 * @prop {} body
 * @prop {} footer
 */
const Section = ({header, body, footer}: SectionProps) => {
  return (
    <Wrapper>
      <CRow>
        <CCol>
          <CCard>
            {header && <CCardHeader>{header}</CCardHeader>}
            <CCardBody>{body}</CCardBody>
            {footer && <CCardFooter>{footer}</CCardFooter>}
          </CCard>
        </CCol>
      </CRow>
    </Wrapper>
  );
};

export default Section;

const Wrapper = styled.section`
  margin-bottom: 20px;
`;
