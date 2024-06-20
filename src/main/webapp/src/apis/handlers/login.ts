import request from 'apis/core';
import {PostUsersLoginRequest} from '../types/PostUsersLoginRequest';
import {PostUsersLoginResponse} from '../types/PostUsersLoginResponse';

export const loginApi = {
  postUsersLogin: async (payload: PostUsersLoginRequest) => {
    const url = `/auth/log-in`;
    return await request.post<PostUsersLoginResponse>(url, payload);
  },

  postUsersAutoLogin: async (jwt: string) => {
    const url = `/users/auto-login`;
    return await request.post(url, {jwt});
  },
};
