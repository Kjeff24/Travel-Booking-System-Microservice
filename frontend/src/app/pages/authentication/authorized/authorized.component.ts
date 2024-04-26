import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';
import { TokenService } from '../../../services/token/token.service';

@Component({
  selector: 'app-authorized',
  templateUrl: './authorized.component.html',
  styleUrls: ['./authorized.component.css'],
})
export class AuthorizedComponent implements OnInit {
  code = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((data) => {
      this.code = data['code'];
      this.getToken();
    });
  }

  // getToken(): void {
  //   this.authService.getToken(this.code).subscribe({
  //     next: (data) => {
  //       this.tokenService.setTokens(
  //         data['access_token'],
  //         data['refresh_token']
  //       );
  //     },
  //     error: (err) => {
  //       console.log(err);
  //     },
  //   });
  // }
  async getToken(): Promise<void> {
    try {
      const data = await this.authService.getToken(this.code).toPromise();
      this.tokenService.setTokens(data['access_token'], data['refresh_token']);
    } catch (err) {
      console.log(err);
    }
  }
  


}
