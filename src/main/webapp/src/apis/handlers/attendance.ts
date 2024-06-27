import request from 'apis/core';
import {PageRequest, PageResponse} from 'components/Table/type';
import {setSearchParams} from "../core/setSearchParams";
import {AttendanceCodeDTO} from "../types/attendance/AttendanceCodeDTO";

export const attendanceApi = {

  getAllAttendanceCode: async (payload: PageRequest) => {
    const url = setSearchParams(`/admin/attendances/code/page`, payload);
    return await request.get<PageResponse<AttendanceCodeDTO>>(url);
  },

  postAttendanceCode: async (payload: PostAttendanceCode) => {
    const url = setSearchParams(`/admin/attendances/code`, payload);
    return await request.post<AttendanceCodeDTO>(url, payload);
  },

  deleteAttendanceCode: async (id: any) => {
    const url = setSearchParams(`/admin/attendances/code`, id);
    return await request.delete<any>(url, id);
  },
};
