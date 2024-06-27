import {GetByGeneration} from "../types/user/GetByGeneration";
import {PageRequest, PageResponse} from "../../components/Table/type";
import {setSearchParams} from "../core/setSearchParams";
import request from "../core";

export const generationWeekApi = {

  getAllGenerationWeeksByGeneration: async (payload: GetByGeneration & PageRequest) => {
    const url = setSearchParams(`/admin/generations/date/page`, payload);
    return await request.get<PageResponse<GenerationWeekDTO>>(url);
  },

};
