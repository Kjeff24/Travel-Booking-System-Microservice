import { Component, OnInit } from '@angular/core';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-userstate',
  standalone: true,
  imports: [],
  template: `
    <p>
      userstate works!
    </p>
  `,
  styles: ``
})
export class UserstateComponent{
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  isOauth2User: boolean;
  userId: string;

  constructor(public tokenService: TokenService) {}

  getLogged(): void {
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isCustomer = this.tokenService.isCustomer();
    this.isOauth2User = this.tokenService.IsOauth2User();
    this.userId = this.tokenService.getUserId();
  }

}
