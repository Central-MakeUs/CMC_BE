/**
 * (테이블)페이지네이션 형식의 API 요청 및 응답을 위한 인터페이스입니다.
 * - 백엔드와 DTO 형식에 대해 협의해 변경될 수 있습니다.
 *
 *
 * @example
 *  getUser: async (payload: Partial<GetUserRequest> & TableRequest) => {
 const baseUrl = `/users`;
 const url = setApiParams(baseUrl, payload);
 return await request.get<TableResponse<GetUserDTO>>(url);
 },
 */

export interface PageResponse<T> {
    contents: T[];
    page: number;
    totalCnt: number;
}

export interface PageRequest {
    page?: number;
    size?: number;
    isExportExcel?: boolean;
}
