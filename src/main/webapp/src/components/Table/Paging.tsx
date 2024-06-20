import {CPagination, CPaginationItem} from '@coreui/react';
import React from 'react';

export interface PaginationProps {
    setCurrentPage: React.Dispatch<React.SetStateAction<number>>;
    current: number;
    totalCnt: number;
    size: number;
}

/**
 * 페이지네이션 관련 파라미터는 백엔드와의 협의 후 변경될 수 있습니다.
 *
 * @prop {} setCurrentPage page state setter
 * @prop {} current page state value
 * @prop {} totalCnt 전체 데이터 개수
 * @prop {} size 한 페이지에서 보여줄 데이터 개수
 */
const Paging = ({setCurrentPage, current, totalCnt, size}: PaginationProps) => {
    const totalPage = Math.ceil(totalCnt / size);
    const lastPageGroup = Math.floor(totalPage / 10);
    const currentPageGroup = Math.floor(current / 10);
    const isLastPage = currentPageGroup === lastPageGroup;
    const pages = Array.from(
        {length: isLastPage ? totalPage - currentPageGroup * 10 : 10},
        (_v, i) => i + 1 + currentPageGroup * 10,
    );

    return (
        <>
            <CPagination>
                <CPaginationItem aria-label='GoFirst' disabled={current < 11} onClick={() => setCurrentPage(0)}>
                    <span aria-hidden='true'>&laquo;</span>
                </CPaginationItem>
                <CPaginationItem aria-label='Previous' disabled={current < 11}
                                 onClick={() => setCurrentPage(current - 10)}>
                    <span aria-hidden='true'>‹</span>
                </CPaginationItem>
                {pages.map(v => (
                    <CPaginationItem key={v} active={current === v - 1} onClick={() => setCurrentPage(v - 1)}>
                        {v}
                    </CPaginationItem>
                ))}
                <CPaginationItem
                    aria-label='Next'
                    disabled={currentPageGroup === lastPageGroup}
                    onClick={() => setCurrentPage(current + 10)}
                >
                    <span aria-hidden='true'>›</span>
                </CPaginationItem>
                <CPaginationItem
                    aria-label='GoLast'
                    disabled={currentPageGroup === lastPageGroup}
                    onClick={() => setCurrentPage(totalPage - 1)}
                >
                    <span aria-hidden='true'>&raquo;</span>
                </CPaginationItem>
            </CPagination>
        </>
    );
};

export default Paging;
