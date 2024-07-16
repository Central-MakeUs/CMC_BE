import request from 'apis/core';
import {PostUsersLoginRequest} from '../types/PostUsersLoginRequest';
import {PostUsersLoginResponse} from '../types/PostUsersLoginResponse';

export const loginApi = {
  postUsersLogin: async (payload: PostUsersLoginRequest) => {
    const url = `/auth/log-in`;
    return await request.post<PostUsersLoginResponse>(url, payload);
  },

  postUsersAutoLogin: async (jwt: string) => {
    const url = `/auth/auto-login`;
    return await request.post<boolean>(url, {accessToken: jwt});
  },
};
