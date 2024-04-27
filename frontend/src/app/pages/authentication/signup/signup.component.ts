import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Signup } from '../../../models/signup';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Login } from '../../../models/login';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  authorize_uri = environment.authorize_uri;

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

  signUpObj: Signup;

  constructor(private http: HttpClient){
    this.signUpObj = new Signup();
    this.loginObj = new Login();
  }

  onSignUp(){

  }

  onLogin(){
    const httpParams = new HttpParams({fromObject: this.params})
    const codeUrl = this.authorize_uri + httpParams.toString();
    location.href = codeUrl;
  }

}
