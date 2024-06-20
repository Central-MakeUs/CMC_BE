export const mockApi = {
  /**
   * 데모를 위한 예시 데이터 및 함수입니다.
   */
  getData: async ({}) => {
    await wait(500);
    const data = generateMockResponse(100);
    return data;
  },
};

const wait = (milliSeconds: number) => new Promise(resolve => setTimeout(resolve, milliSeconds));

export interface MockRequest {
  registerStartDate: string;
  registerEndDate: string;
  loginStartDate: string;
  loginEndDate: string;
  search: 'id' | 'manager' | 'phone' | 'department' | 'duty';
  status: boolean;
  order: 'createdAtDesc' | 'createdAtAsc' | 'loginedAtDesc' | 'loginedAtAsc';
  searchValue: string;
}

export interface MockResponse {
  index: string;
  email: string;
  manager: string;
  phone: string;
  department: string;
  duty: string;
  status: boolean;
  createdAt: string;
  loginedAt: string;
}

export const generateMockResponse = (count: number): MockResponse[] => {
  return Array.from({length: count}, (_, i) => {
    return {
      index: `${i}`,
      email: `david0218@naver.com`,
      manager: '홍길동',
      phone: '010-XXXX-XXXX',
      department: 'YY부',
      duty: 'ZZZ',
      status: i % 3 === 0 ? true : false,
      image: 'https://gridge.co.kr/static/media/img_gridge_logo_blue.64acce5523c3be05fa6f4752b51d65f3.svg',
      createdAt: '2023-07-17 08:59:37',
      loginedAt: '2023-07-17 08:59:37',
    };
  });
};
