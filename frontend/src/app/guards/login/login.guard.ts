import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../../services/token/token.service';

export const loginGuard: CanActivateFn = (route, state) => {
  const token = inject(TokenService);
  const router = inject(Router);

    if (token.isLoggedIn() == false) {
        router.navigate(['']);
        alert('You need to log in')
        return false;
    }
  return token.isLoggedIn();
};
