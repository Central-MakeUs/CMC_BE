export interface GetTableResponseTypeParams<T> {
  src?: T;
  size?: number;
  page: number;
}

/**
 * 통 배열 데이터를 테이블 페이지네이션 형식에 맞게 변환해, 현재 페이지의 데이터만 가져옵니다.
 * 백엔드에서 내려주는 DTO 형식에 따라 변경될 수 있습니다.
 * 이 탬플릿이 따르는 형식은 아래와 같습니다.
 *
 * @example
 * interface TableResponse<T> {
 *   page :	number;
 *   totalCnt : number;
 *   contents : T[];
 * }
 *
 * @param {GetTableResponseTypeParams<T>} 데이터, 한페이지 개수(size), 현재 페이지 인덱스(page)를 인자로 전달합니다.
 * @returns {contents : T, totalCnt : number, page : number}
 */
export const getTableResponseType = <T extends object[]>({src, size = 10, page}: GetTableResponseTypeParams<T>) => {
  if (!src) {
    return {contents: [] as T[], totalCnt: 0, page: 0};
  }
  const contents = src.slice(size * page, size * page + size);
  return {contents: contents as T, totalCnt: src.length, page};
};
