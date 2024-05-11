import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';
import { environment } from '../../../environments/environment';

const ACCESS_TOKEN = 'access_token';
const REFRESH_TOKEN = 'refresh_token';
const CODE_VERIFIER = 'code_verifier';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  constructor() {}

  setTokens(access_token: string, refresh_token: string): void {
    sessionStorage.removeItem(ACCESS_TOKEN);
    sessionStorage.setItem(ACCESS_TOKEN, access_token);
    sessionStorage.removeItem(REFRESH_TOKEN);
    sessionStorage.setItem(REFRESH_TOKEN, refresh_token);
  }

  getAccessToken(): string | null {
    return sessionStorage.getItem(ACCESS_TOKEN);
  }

  getRefreshToken(): string | null {
    return sessionStorage.getItem(REFRESH_TOKEN);
  }

  clear(): void {
    sessionStorage.removeItem(ACCESS_TOKEN);
    sessionStorage.removeItem(REFRESH_TOKEN);
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem(ACCESS_TOKEN) != null;
  }

  hasRole(role: string): boolean {
    if (!this.isLoggedIn()) {
      return false;
    }
    const token = this.getAccessToken();
    const payload = token.split('.')[1];
    const decodedPayload = atob(payload);
    const values = JSON.parse(decodedPayload);
    const roles = values.roles;
    return roles.indexOf(role) >= 0;
  }

  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }

  isCustomer(): boolean {
    return this.hasRole('CUSTOMER');
  }

  IsOauth2User(): boolean {
    return this.hasRole('OAUTH2');
  }

  getUserId() : string {
    if (!this.isLoggedIn()) {
      return null; 
    }
    const token = this.getAccessToken();
    const payload = token.split(".")[1];
    const decodedPayload = atob(payload);
    const values = JSON.parse(decodedPayload);
    return values.userId;
  }

  setCodeVerifier(code_verifier: string): void {
    if (localStorage.getItem(CODE_VERIFIER)) {
      this.deleteVerifier();
    }
    const encrypted = CryptoJS.AES.encrypt(
      code_verifier,
      environment.secret_pkce
    );
    localStorage.setItem(CODE_VERIFIER, encrypted.toString());
  }

  getCodeVerifier(): string {
    const encrypted = localStorage.getItem(CODE_VERIFIER);
    const decrypted = CryptoJS.AES.decrypt(
      encrypted,
      environment.secret_pkce
    ).toString(CryptoJS.enc.Utf8);
    return decrypted;
  }

  deleteVerifier(): void {
    localStorage.removeItem(CODE_VERIFIER);
  }

  getTokenExpiration() : number {
    if (!this.isLoggedIn()) {
      return null; 
    }
    const token = this.getAccessToken();
    const payload = token.split(".")[1];
    const decodedPayload = atob(payload);
    const values = JSON.parse(decodedPayload);
    return values.exp;
  }

  
}
