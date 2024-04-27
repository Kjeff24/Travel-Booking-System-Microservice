import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Login } from '../../models/login';
import { HttpParams } from '@angular/common/http';
import { TokenService } from '../../services/token/token.service';
import { CommonModule } from '@angular/common';
import CryptoJS from 'crypto-js';

const CHARACTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  signup_uri = environment.signup_url;
  authorize_uri = environment.authorize_uri;
  logout_uri = environment.logout_url;
  isLoggedIn: boolean;
  isAdmin: boolean;

  params: any = {
    client_id: environment.client_id,
    redirect_uri: environment.redirect_uri,
    scope: environment.scope,
    response_type: environment.response_type,
    response_mode: environment.response_mode,
    code_challenge_method: environment.code_challenge_method
  }
  
  loginObj: Login;

  constructor(
    private tokenService: TokenService
  ){}

  ngOnInit(): void {
    this.getLogged();
  }

  onSignUp(): void{
    location.href = this.signup_uri;
  }


  onLogin(): void{
    const code_verifier = this.generateCodeVerifier();
    this.tokenService.setCodeVerifier(code_verifier);
    this.params.code_challenge = this.generateCodeChallenge(code_verifier);
    console.log("login")
    const httpParams = new HttpParams({fromObject: this.params})
    const codeUrl = this.authorize_uri + httpParams.toString();
    location.href = codeUrl;
  }

  onLogout(): void {
    this.tokenService.clear();
    location.href = this.logout_uri;
  }

  getLogged(): void{
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
  }

  generateCodeVerifier(): string{
    let result = '';
    const char_length = CHARACTERS.length;
    for(let i =0; i < 44; i++) {
      result += CHARACTERS.charAt(Math.floor(Math.random() * char_length));
    }
    return result;
  }

  generateCodeChallenge(code_verifier: string): string {
    const codeverifierHash = CryptoJS.SHA256(code_verifier).toString(CryptoJS.enc.Base64);
    const code_challenge = codeverifierHash
    .replace(/=/g, '')
    .replace(/\+/g, '-')
    .replace(/\//g, '_');
    return code_challenge;
  }


}
