import {CTable, CTableBody, CTableDataCell, CTableHead, CTableHeaderCell, CTableRow} from '@coreui/react';
import {Column, Item} from '@coreui/react/dist/components/table/types';
import React, {Dispatch, ReactNode, SetStateAction, useEffect, useState} from 'react';
import Pagination from './Paging';
import styled from 'styled-components';
import {filterRowData, getCustomColumn, getCustomRow} from './utils';
import Loading from './Loading';
import {FlexBox} from 'components/FlexBox';
import {Spacing} from 'components/Spacing';
import {PageResponse} from './type';

export interface CustomCellItem<T> {
    [key: string]: (data: T) => ReactNode;
}

export interface CustomColumnItem {
    [key: string]: ReactNode;
}

export type Status = 'error' | 'success' | 'loading';

export interface TableProps<T> {
    column: Column[];
    data?: PageResponse<T>;
    renderColumnData?: CustomCellItem<T>;
    renderColumnHead?: CustomColumnItem;
    paginationState: [number, Dispatch<SetStateAction<number>>];
    size?: number;
    onRowClick?: (data: T) => void;
    selectableKey?: string;
    updateSelection?: Dispatch<React.SetStateAction<any[]>>;
    hidetotalCnt?: boolean;
    hidePagination?: boolean;
    status?: Status;
}

/**
 * 테이블 공용 컴포넌트
 * 더미페이지에 자세한 사용 예시가 있습니다.
 *
 * @prop {Column[]} column 테이블 컬럼 배열
 * @prop {TableReponse<T>} data 패칭 데이터 (useQuery에서 제공해주는 data)
 * @prop {CustomCellItem<T>} renderColumnData 특정 행의 cell을 별도 컴포넌트로 렌더링합니다
 * @prop {CustomColumnItem} renderColumnHead 특정 행의 head를 별도 컴포넌트로 렌더링합니다
 * @prop {number} size 한 페이지에서 보여주는 데이터의 개수
 * @prop {[number, Dispatch<SetStateAction<number>>]} paginationState useState 반환값 그대로 넣으시면 됩니다
 * @prop {(data: T) => void} onRowClick 해당 열을 클릭시 실행. 매개변수로 해당 item data가 들어갑니다
 * @prop {boolean} hidetotalCnt totalCnt를 렌더링하지 않습니다
 * @prop {boolean} hidePagination pagination바를 렌더링하지 않습니다
 * @prop {Status} status api promise status (useQuery에서 제공해주는 status)
 *
 * @prop {string} selectableKey (열 선택 기능) 선택의 기준이 될 key string
 * @prop {Dispatch<React.SetStateAction<any[]>>} updateSelection (열 선택 기능) 선택한 key 배열(string[])의 setter
 *
 */
const Table = <T extends Item>({
                                   column,
                                   data = {contents: [], totalCnt: 0, page: 0},
                                   renderColumnData,
                                   renderColumnHead,
                                   size = 10,
                                   paginationState,
                                   onRowClick,
                                   selectableKey,
                                   updateSelection,
                                   hidetotalCnt,
                                   hidePagination,
                                   status,
                               }: TableProps<T>) => {
    const itemList = renderColumnData ? getCustomRow(data.contents, renderColumnData) : data.contents;
    const columnList = getCustomColumn(column, renderColumnHead);
    const [selection, setSelection] = useState(new Set());

    //로딩중에도 페이지네이션 유지 위함
    const [total, setTotal] = useState(data.totalCnt);

    useEffect(() => {
        data.totalCnt !== 0 && setTotal(data.totalCnt);
    }, [data]);

    const onChangeSelect = (value: string | number) => {
        // 기존의 selection으로 새로운 Set 생성
        const newSelection = new Set(selection);
        if (newSelection.has(value)) {
            // value가 있으면 삭제 (checked가 false이기 때문)
            newSelection.delete(value);
        } else {
            // value가 없으면 추가 (checked가 true이기 때문)
            newSelection.add(value);
        }
        // 새로운 Set으로 state 변경
        setSelection(newSelection);
        updateSelection?.([...newSelection]);
    };

    const isSelectedAll = () => {
        return selection.size === itemList.length;
    };
    const onChangeSelectAll = () => {
        if (isSelectedAll()) {
            setSelection(new Set());
            updateSelection?.([]);
        } else {
            const allCheckedSelection = new Set(itemList.map(item => item[selectableKey!]));
            setSelection(allCheckedSelection);
            updateSelection?.([...allCheckedSelection]);
        }
    };

    return (
        <Wrapper>
            {!hidetotalCnt && <div>총 {total.toLocaleString('ko-KR')}건</div>}
            <Spacing size={8}/>
            <CTable hover striped responsive>
                <CTableHead>
                    <CTableRow>
                        {selectableKey && (
                            <CTableHeaderCell scope='col'>
                                <input type='checkbox' checked={isSelectedAll()} onChange={onChangeSelectAll}/>
                            </CTableHeaderCell>
                        )}
                        {columnList.map(c => (
                            <CTableHeaderCell scope='col' key={c.key} style={c._style}>
                                {c.render}
                            </CTableHeaderCell>
                        ))}
                    </CTableRow>
                </CTableHead>
                {((status !== undefined && status === 'success') || status === undefined) && (
                    <CTableBody>
                        {itemList.map((item, idx) => (
                            <CTableRow key={`${item.id}-${idx}`}>
                                {selectableKey && (
                                    <CTableDataCell>
                                        <input
                                            type='checkbox'
                                            checked={selection.has(item[selectableKey])}
                                            onChange={() => onChangeSelect(item[selectableKey])}
                                        />
                                    </CTableDataCell>
                                )}
                                {Object.keys(filterRowData(item, column)).map(cell => (
                                    <CTableDataCell key={cell} onClick={() => onRowClick?.(item)}>
                                        {item[cell]}
                                    </CTableDataCell>
                                ))}
                            </CTableRow>
                        ))}
                    </CTableBody>
                )}
            </CTable>
            {status !== undefined && status === 'success' && itemList.length === 0 && <Empty>데이터가 없습니다</Empty>}
            {status === 'loading' && <Loading height={300}/>}
            {!hidePagination && (
                <FlexBox>
                    <Pagination current={paginationState[0]} setCurrentPage={paginationState[1]} totalCnt={total}
                                size={size}/>
                </FlexBox>
            )}
        </Wrapper>
    );
};
export default React.memo(Table);

const Wrapper = styled.div``;

const Empty = styled(FlexBox)`
  height: 100px;
  color: #c0c0c0;
`;
