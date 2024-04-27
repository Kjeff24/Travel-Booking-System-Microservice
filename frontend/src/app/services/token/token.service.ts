import { Injectable } from '@angular/core';

const ACCESS_TOKEN = 'access_token';
const REFRESH_TOKEN = 'refresh_token';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  setTokens(access_token: string, refresh_token: string): void {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.setItem(ACCESS_TOKEN, access_token);
    localStorage.removeItem(REFRESH_TOKEN);
    localStorage.setItem(REFRESH_TOKEN, refresh_token);
  }

  getAccessToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN);
  }

  clear(): void {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
  }

  isLoggedIn(): boolean{
    return localStorage.getItem(ACCESS_TOKEN) != null;
  }
  
  isAdmin(): boolean {
    if(!this.isLoggedIn()) {
      return false
    }
    const token = this.getAccessToken();
    const payload = token.split(".")[1];
    const decodedPayload = atob(payload);
    const values = JSON.parse(decodedPayload);
    const roles = values.roles;
    if(roles.indexOf('ADMIN') < 0) {
      return false;
    }
    return true;
  }
}