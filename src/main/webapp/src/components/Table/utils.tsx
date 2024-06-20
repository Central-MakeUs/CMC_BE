import {Item, Column} from '@coreui/react/dist/components/table/types';
import {CustomCellItem, CustomColumnItem} from '.';
import React, {ReactNode} from 'react';

interface CustomColumn extends Column {
  render: ReactNode;
}

/**
 * 패칭 데이터 중 table column에 있는 값만 반환
 * @param data 원본 데이터 (패칭한 데이터)
 * @param column table에서 보여줄 column key
 */
const filterRowData = <T extends Item>(data: T, column: Column[]): Partial<T> => {
  const map = new Map();
  column.forEach(c => {
    const key = c.key;
    map.set(key, data[key]);
  });
  return Object.fromEntries(map);
};

/**
 * 버튼, input 등의 렌더링할 컴포넌트를 item으로 반환
 * @param data 렌더링할 data
 * @param renderColumnData cell에 렌더링할 컴포넌트
 */
const getCustomRow = <T,>(data: T[], renderColumnData: CustomCellItem<T>): T[] => {
  let customRow = data;
  Object.keys(renderColumnData).forEach(v => {
    customRow = customRow.map(row => {
      return {...row, [v]: renderColumnData[v](row)};
    });
  });
  return customRow;
};

/**
 * 컬럼에 렌더링할 컴포넌트 변환
 * @param columns
 * @param renderColumnHead
 */
const getCustomColumn = (columns: Column[], renderColumnHead?: CustomColumnItem): CustomColumn[] => {
  let customColumn: CustomColumn[] = columns.map(v => {
    return {...v, render: <>{v.label}</>};
  });

  renderColumnHead &&
    Object.keys(renderColumnHead).forEach(columnKey => {
      customColumn = customColumn.map(col =>
        col.key === columnKey ? {...col, render: renderColumnHead[columnKey]} : col,
      );
    });
  return customColumn;
};

/**
 * 테이블 컴포넌트를 위한 유틸함수들입니다.
 * 내부 구현을 모르고 사용하셔도 무방합니다.
 */
export {filterRowData, getCustomRow, getCustomColumn};
