import React from 'react';
import styled from 'styled-components';

/**
 * 빈 공간(열)을 간편하게 추가할 수 있습니다
 * @prop {number} size
 */
export const Spacing = ({size}: {size: number}) => {
  return <Wrapper size={size} />;
};

const Wrapper = styled.div<{size: number}>`
  height: ${({size}) => `${size}px`};
`;
