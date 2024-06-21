interface Time {
  hour: string;
  minute: string;
}

interface PostAttendanceCode {
  generation: string;
  week: string;
  hour: string;
  startTime: Time;
  endTime: Time;
  lateMinute: string;
}

