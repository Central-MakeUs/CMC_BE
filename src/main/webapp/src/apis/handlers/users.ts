import request from 'apis/core';
import {PageRequest, PageResponse} from 'components/Table/type';
import {GetUsersByGeneration} from "../types/user/GetUsersByGeneration";
import {setSearchParams} from "../core/setSearchParams";
import {UserDTO} from "../types/user/UserDTO";

export const userApi = {

    getAllUsersByGeneration: async (payload: GetUsersByGeneration & PageRequest) => {
        const url = setSearchParams(`/admin/users/all`, payload);
        return await request.get<PageResponse<UserDTO>>(url);
    },

    handleSignUpAprrove: async (payload: { userId: number; approve: boolean }) => {
        const url = setSearchParams(`/admin/users/management/user`, payload);
        return await request.get<Boolean>(url);
    },
};
