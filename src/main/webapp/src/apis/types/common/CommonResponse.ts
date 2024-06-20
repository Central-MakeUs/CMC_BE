export interface CommonResponse<T> {
    isSuccess: boolean;
    message: string;
    code: string;
    result: T;
}
