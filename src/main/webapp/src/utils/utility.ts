import {GetUsersUserIdResponse} from '../apis/types/GetUsersUserIdResponse';
import {JWT_KEY} from '../config/constant';

/**
 * @param {string} email 이메일 정규식 유효성 검사
 * @returns {boolean}
 */
export const isValidEmail = (email: string) => {
  return email.match(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,4}$/i) !== null;
};

export const isValidPassword = (password: string) => {
  return password.match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/);
};

/**
 * @param {string} phoneNumber 전화번호 정규식 유효성 검사
 * @returns {boolean}
 */
export const isValidPhoneNumber = (phoneNumber: string) => {
  return phoneNumber.match(/^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/);
};

/**
 * @param {string} input 전화번호 형식으로 정규식 변환
 * @returns {string|undefined}
 */
export const formatPhoneNumber = (input?: string) => {
  return input
    ?.replace(/[^0-9]/g, '')
    .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, '$1-$2-$3')
    .replace(/(-{1,2})$/g, '');
};

/**
 * @param {string} value string 형식의 숫자 입력
 * @returns {number}
 *
 * @example localeStringToNumber('13,000');
 * //return 13000
 *
 * useInput의 두번째 인자로 주로 사용합니다
 * @example const [localStringValue, onChangeLocaleStringValue] = useInput('', localeStringToNumber);
 */
export const localeStringToNumber = (value?: string) => {
  if (!value) return;
  const parsed = value
    .replace(/[^0-9 ,]/g, '')
    .trim()
    .replace(/,/g, '');
  return Number(parsed);
};

/**
 *
 * @param value 숫자 스트링
 * @example
 * localeStringToNumber을 사용하는 useInput의 value에 주로 사용합니다.
 * <Row.Input
      label='숫자(세자리마다 콤마 입력)'
      onChange={onChangeLocaleStringValue}
      value={StringNumberToLocaleString(localStringValue)}
      placeholder='숫자를 입력해주세요'
    />
 */
export const StringNumberToLocaleString = (value?: string) => {
  const parsedValue = Number(value || '');

  if (Number.isNaN(parsedValue)) {
    return '';
  }
  return parsedValue ? parsedValue.toLocaleString('ko-KR') : '';
};

export const getJwt = () => window.localStorage.getItem(JWT_KEY) ?? '';

export const saveJwt = (jwt: string) => {
  window.localStorage.setItem(JWT_KEY, jwt);
};

export const clearJwt = () => {
  window.localStorage.removeItem(JWT_KEY ?? '');
};

export const saveUser = (user: GetUsersUserIdResponse) => {
  window.localStorage.setItem(process.env.REACT_APP_LOGIN_INFO ?? '', JSON.stringify(user) ?? '');
};

export const clearUser = () => {
  window.localStorage.removeItem(process.env.REACT_APP_LOGIN_INFO ?? '');
};

export const getUserEmail = () => {
  const loginInfo = JSON.parse(window.localStorage.getItem(process.env.REACT_APP_LOGIN_INFO ?? '') ?? '');
  return loginInfo.email ?? '';
};

export const getUserName = (): string => {
  const loginInfo = JSON.parse(window.localStorage.getItem(process.env.REACT_APP_LOGIN_INFO ?? '') ?? '');
  return loginInfo.name ?? '';
};
