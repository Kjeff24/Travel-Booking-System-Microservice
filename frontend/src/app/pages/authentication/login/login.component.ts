import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Login } from '../../models/login';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-signup',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  loginObj: Login;

  constructor(private http: HttpClient){
    this.loginObj = new Login();
  }

  onLogin(){
    
  }
}
