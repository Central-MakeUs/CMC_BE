import {CSpinner} from '@coreui/react';
import React from 'react';
import styled from 'styled-components';

/**
 * 테이블 데이터가 로딩될동안 스피너를 렌더링합니다.
 * @prop {number} height 컴포넌트 높이 (기본값 auto)
 */
const Loading = ({height}: {height?: number}) => {
  return (
    <Wrapper height={height}>
      <CSpinner />
    </Wrapper>
  );
};

export default Loading;

const Wrapper = styled.div<{height?: number}>`
  height: ${({height}) => (height ? `${height}px` : `auto`)};
  display: flex;
  justify-content: center;
  align-items: center;
`;
