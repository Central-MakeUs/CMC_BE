import React, {useEffect} from 'react';
import {useState} from 'react';
import Select, {MultiValue, SingleValue} from 'react-select';
import styled from 'styled-components';

type OptionValue = string | number | null;
export interface Option {
  value: OptionValue;
  label: string;
}

export type SingleSelect = SingleValue<Option>;
export type MultiSelect = MultiValue<Option>;

export type SelectReturnType<T, RenderType> = T extends MultiSelect
  ? [OptionValue[], RenderType, React.Dispatch<React.SetStateAction<MultiSelect>>]
  : [OptionValue, RenderType, React.Dispatch<React.SetStateAction<SingleSelect>>];

const isMultiSelect = (value: SingleSelect | MultiSelect): value is MultiValue<Option> => {
  return Array.isArray(value as MultiValue<Option>);
};

export interface SelectInputProps {
  placeholder?: string;
  width?: number;
}

/**
 *
 * 단일선택(SingleSelect)와 다중선택(MultiSelect)를 위한 컴포넌트입니다.
 * 훅의 두번째 인자(initialValue)에 배열 타입의 객체가 들어오면 MultiSelect 컴포넌트로 작동합니다.
 * 초기값이 없지만 MultiSelect로 사용하려면 빈 배열을 사용해주세요.
 * @param options
 * @param initialValue
 * @param onChangeFn
 *
 * @example
 *  const [selectValue, SingleSelect] = useSelect(optionData);
    const [selectMulti, MutiSelect] = useSelect(optionData, []);
     // ...

    <SingleSelect placeholder={'단일 선택'}/>
    <MutiSelect placeholder={'중복 선택'}/>
 * @returns [value, JSX.Element]
 */
const useSelect = <T extends SingleSelect | MultiSelect = SingleSelect>(
  options: Option[],
  initialValue: T = null as T,
  onChangeFn?: () => void,
) => {
  const isMulti = isMultiSelect(initialValue);
  const [value, setValue] = useState<T>(initialValue);

  useEffect(() => {
    if (value && value !== initialValue) {
      onChangeFn?.();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [value]);

  const SelectInput = ({placeholder, width}: SelectInputProps) => {
    return (
      <SelectStyles width={width}>
        <Select
          isMulti={isMulti}
          onChange={selectedOption => {
            setValue((selectedOption ?? null) as T);
          }}
          value={value}
          options={options}
          placeholder={placeholder}
        />
      </SelectStyles>
    );
  };

  return [
    isMulti ? (value as MultiSelect).map(v => v.value) : (value as SingleSelect)?.value,
    SelectInput,
    setValue,
  ] as SelectReturnType<T, typeof SelectInput>;
};

export default useSelect;

const SelectStyles = styled.div<{width: number | undefined}>`
  & > * {
    width: ${({width}) => (width ? `${width}px` : `100%`)};
  }
`;
