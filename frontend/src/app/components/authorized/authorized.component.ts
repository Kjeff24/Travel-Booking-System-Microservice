import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { TokenService } from '../../services/token/token.service';
import { NavbarComponent } from '../navbar/navbar.component';

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
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((data) => {
      this.code = data['code'];
      const code_verifier = this.tokenService.getCodeVerifier();
      this.tokenService.deleteVerifier();
      this.getToken(this.code, code_verifier);
    });
  }

  getToken(code: string, code_verifier: string): void {
    this.authService.getToken(code, code_verifier).subscribe({
      next: (data) => {
        this.tokenService.setTokens(
          data['access_token'],
          data['refresh_token']
        );
        this.router.navigate(['']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  


}
