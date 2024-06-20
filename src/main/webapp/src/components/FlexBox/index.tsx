import React from 'react';
import {CSSProperties} from '@emotion/serialize';
import {HTMLAttributes, ReactNode} from 'react';
import styled from 'styled-components';

export interface FlexBoxProps extends HTMLAttributes<HTMLDivElement> {
  align?: CSSProperties['alignItems'];
  justify?: CSSProperties['justifyContent'];
  direction?: CSSProperties['flexDirection'];
  gap?: CSSProperties['gap'];
  children: ReactNode;
  fullWidth?: boolean;
}

export type flexboxPropsKey = 'align' | 'justify' | 'direction' | 'gap';
/**
 *
 * @prop {} align : align-items 속성 (기본값 : center)
 * @prop {} jusitfy : justify-content 속성 (기본값 : center)
 * @prop {} direction : direction 속성 (기본값 : row)
 * @prop {} gap : gap 속성
 * 
 * @example
 * <FlexBox justify={'flex-start'} gap={28}>
        <div>leftElement</div>
        <div>rightElement</div>
    </FlexBox>
 */
export const FlexBox = ({
  align = 'center',
  justify = 'center',
  direction = 'row',
  gap = 0,
  children,
  ...props
}: FlexBoxProps) => {
  return (
    <Wrapper align={align} justify={justify} direction={direction} gap={gap} {...props}>
      {children}
    </Wrapper>
  );
};

const Wrapper = styled.div<FlexBoxProps>`
  display: flex;
  align-items: ${({align}) => align};
  justify-content: ${({justify}) => justify};
  flex-direction: ${({direction}) => direction};
  gap: ${({gap}) => `${gap}px`};
  width: ${({fullWidth}) => (fullWidth ? `100%` : `auto`)};
`;
