import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Login } from '../../models/login';
import { HttpParams } from '@angular/common/http';
import { TokenService } from '../../services/token/token.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
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
    code_challenge_method: environment.code_challenge_method,
    code_challenge: environment.code_challenge,
  }
  
  loginObj: Login;

  constructor(
    private tokenService: TokenService
  ){}

  ngOnInit(): void {
    this.getLogged();
  }


  onLogin(): void{
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


}
