import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Login } from '../../models/login';
import { HttpParams } from '@angular/common/http';
import { TokenService } from '../../services/token/token.service';
import { CommonModule } from '@angular/common';
import CryptoJS from 'crypto-js';
import { BookingService } from '../../services/booking/booking.service';
import { CartService } from '../../services/cart/cart.service';
import { UserstateComponent } from '../userstate/userstate.component';
import { Token } from '@angular/compiler';

const CHARACTERS =
  'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent  {
  signup_uri = environment.auth_server_uri + '/signup';
  authorize_uri = environment.auth_server_uri + '/oauth2/authorize?';
  logout_uri = environment.auth_server_uri + '/logout';
  change_password_uri = environment.auth_server_uri + '/change-password';
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  userId: string;
  totalCartsItem: number;

  params: any = {
    client_id: environment.client_id,
    redirect_uri: environment.redirect_uri,
    scope: environment.scope,
    response_type: environment.response_type,
    response_mode: environment.response_mode,
    code_challenge_method: environment.code_challenge_method,
  };

  loginObj: Login;

  constructor(
    private bookingService: BookingService,
    private cartService: CartService,
    private tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
    this.getLogged();
    this.getCartsTotalQuantity();
  }

  onSignUp(): void {
    location.href = this.signup_uri;
  }

  onLogin(): void {
    const code_verifier = this.generateCodeVerifier();
    this.tokenService.setCodeVerifier(code_verifier);
    this.params.code_challenge = this.generateCodeChallenge(code_verifier);
    const httpParams = new HttpParams({ fromObject: this.params });
    const codeUrl = this.authorize_uri + httpParams.toString();
    location.href = codeUrl;
  }

  onLogout(): void {
    this.tokenService.clear();
    location.href = this.logout_uri;
  }

  onChangePassword(): void {
    location.href = this.change_password_uri + '/' + this.userId;
  }

  getLogged(): void {
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isCustomer = this.tokenService.isCustomer();
    this.userId = this.tokenService.getUserId();
  }

  getCartsTotalQuantity(): void {
    if (this.isLoggedIn) {
      this.bookingService.getCartsTotalQuantity(this.userId).subscribe({
        next: (data: any) => {
          this.totalCartsItem = data.body;
        },
      });
    } else {
      this.totalCartsItem = this.cartService.getTotalQuantity();
    }
  }

  generateCodeVerifier(): string {
    let result = '';
    const char_length = CHARACTERS.length;
    for (let i = 0; i < 44; i++) {
      result += CHARACTERS.charAt(Math.floor(Math.random() * char_length));
    }
    return result;
  }

  generateCodeChallenge(code_verifier: string): string {
    const codeverifierHash = CryptoJS.SHA256(code_verifier).toString(
      CryptoJS.enc.Base64
    );
    const code_challenge = codeverifierHash
      .replace(/=/g, '')
      .replace(/\+/g, '-')
      .replace(/\//g, '_');
    return code_challenge;
  }
}
