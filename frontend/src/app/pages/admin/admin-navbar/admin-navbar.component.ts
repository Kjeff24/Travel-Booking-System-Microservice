import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryItem } from '../../../models/category-item';
import { TokenService } from '../../../services/token/token.service';
import { environment } from '../../../../environments/environment';
import { UserstateComponent } from '../../../components/userstate/userstate.component';

@Component({
  selector: 'app-admin-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './admin-navbar.component.html',
  styleUrl: './admin-navbar.component.css'
})
export class AdminNavbarComponent extends UserstateComponent{
  logout_uri = environment.auth_server_uri + '/logout';
  constructor(public override tokenService: TokenService){
    super(tokenService)
  }

  onLogout(): void {
    this.getLogged();
    this.tokenService.clear();
    location.href = this.logout_uri;
  }

}
