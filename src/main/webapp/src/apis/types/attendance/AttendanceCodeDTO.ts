export interface AttendanceCodeDTO {
  id: string;
  generation: string;
  week: string;
  hour: number;
  generationWeeksInfo: any; // TODO GenerationWeeksInfo 오브젝트인데 파싱이 안됨
  startTime: string;
  endTime: string;
  lateMinute: string;
}

