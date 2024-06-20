export interface PostUsersLoginResponse {
  id: number;
  jwt: string;
  // TODO 테스트용이므로 삭제 필요
  accessToken: string;
}
