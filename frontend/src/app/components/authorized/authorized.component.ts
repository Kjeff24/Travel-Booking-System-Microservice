import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { TokenService } from '../../services/token/token.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { CartService } from '../../services/cart/cart.service';
import { BookingService } from '../../services/booking/booking.service';
import { OrderService } from '../../services/order/order.service';

@Component({
  selector: 'app-authorized',
  templateUrl: './authorized.component.html',
  styleUrls: ['./authorized.component.css'],
})
export class AuthorizedComponent implements OnInit {
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  userId: string;
  code = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private orderService: OrderService,
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router,
    private cartService: CartService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((data) => {
      this.code = data['code'];
      const code_verifier = this.tokenService.getCodeVerifier();
      this.tokenService.deleteVerifier();
      this.getToken(this.code, code_verifier);
    });
  }

  getLogged(): void {
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isCustomer = this.tokenService.isCustomer();
    this.userId = this.tokenService.getUserId();
  }

  getToken(code: string, code_verifier: string): void {
    this.authService.getToken(code, code_verifier).subscribe({
      next: (data) => {
        this.tokenService.setTokens(
          data['access_token'],
          data['refresh_token']
        );

        this.getLogged();

        const cartItems = this.cartService.getCartItems();
        if (cartItems.length > 0) {
          this.orderService.addAllCartItems(cartItems, this.userId).subscribe({
            next: () => {
              this.cartService.clearCart();
              this.router.navigate(['']);
            },
            error: (error) => {
              console.error('Error sending cart items to backend:', error);
              this.cartService.clearCart();
              this.router.navigate(['']);
              window.location.reload();
            },
          });
        } else {
          this.router.navigate(['']);
        }

        this.router.navigate(['']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
  


}
