import dayjs from 'dayjs';

export const date = {
  /**
   * dateTime 객체를 string 문자열로 변환합니다
   * 백엔드와 날짜 형식을 협의 후에 사용해주세요. 불필요한 함수인 경우엔 삭제해주셔도 좋습니다.
   *
   * @param {Date | null} source dateTime 객체
   * @param {boolean} withTime 시간 정보를 포함할지 유무
   * @returns {string } YYYY-MM-DD HH:mm
   */
  getDateToStringFormat: (source: Date | null, withTime = false) => {
    if (source) {
      return withTime ? dayjs(source).format('YYYY-MM-DD HH:mm') : dayjs(source).format('YYYY-MM-DD');
    } else {
      return;
    }
  },

  /**
   * @param source string 형식의 날짜
   * @returns {string} YYYY년 MM월 DD일
   */
  formatDate: (source: string) => {
    return dayjs(source).format(`YYYY년 MM월 DD일`);
  },

  /**
   * @param source string 형식의 날짜
   * @returns {string} HH시 mm분
   */
  formatTime: (source: string) => {
    return dayjs(source).format(`HH시 mm분`);
  },
};
