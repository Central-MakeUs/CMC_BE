export const setSearchParams = (url: string, args: object) => {
  const params = Object.entries(args)
    .filter(([, value]) => value !== undefined)
    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
    .join('&');
  return `${url}?${params}`;
};
