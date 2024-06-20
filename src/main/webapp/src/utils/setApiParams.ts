/**
 *
 * @param url baseUrl
 * @param args 쿼리파라미터 객체
 * @returns {string}
 *
 * @example
 * setApiParams('example',{search : 'foo', order : 'desc'});
 * // return 'example?search=foo&order=desc'
 */
export const setApiParams = (url: string, args: object) => {
  const params = Object.entries(args)
    .filter(([, value]) => value !== undefined)
    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
    .join('&');
  return `${url}?${params}`;
};
