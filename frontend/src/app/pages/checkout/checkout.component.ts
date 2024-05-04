import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { BookingService } from '../../services/booking/booking.service';
import { TokenService } from '../../services/token/token.service';
import { CartService } from '../../services/cart/cart.service';
import { UserstateComponent } from '../../components/userstate/userstate.component';

declare var PaystackPop: any;

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, RouterLink, NavbarComponent, FooterComponent],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css',
})
export class CheckoutComponent  extends UserstateComponent {
  totalCartItems: number;
  totalCartItemsPrice: number;
  reference: string;
  title: string;
  email: string = '';
  amount: number = 0;

  constructor(
    private bookingService: BookingService,
    public override tokenService: TokenService,
    private cartService: CartService
  ) {
    super(tokenService)
  }

  ngOnInit(): void {
    this.reference = `ref-${Math.ceil(Math.random() * 10e13)}`;
    this.getLogged();
    this.getCartsTotalQuantity();
    this.getCartsTotalPrice();
  }

  getCartsTotalQuantity(): void {
    if (this.isLoggedIn) {
      this.bookingService.getCartsTotalQuantity(this.userId).subscribe({
        next: (data: any) => {
          this.totalCartItems = data.body;
        },
      });
    } else {
      this.totalCartItems = this.cartService.getTotalQuantity();
    }
  }

  getCartsTotalPrice(): void {
    if (this.isLoggedIn) {
      this.bookingService.getCartItemsTotalPrice(this.userId).subscribe({
        next: (data: any) => {
          this.totalCartItemsPrice = data.body;
        },
      });
    } else {
      this.totalCartItemsPrice = this.cartService.getTotalPrice();
    }
  }

  payWithPaystack() {
    let handler = PaystackPop.setup({
      key: 'pk_test_085824e3d504411b6e32f41420095f8899ace069', // Replace with your public key
      email: this.email,
      amount: this.amount * 100,
      ref: '' + Math.floor(Math.random() * 1000000000 + 1),
      onClose: () => {
        alert('Window closed.');
      },
      callback: (response: any) => {
        let message = 'Payment complete! Reference: ' + response.reference;
        alert(message);
      },
    });

    handler.openIframe();
  }
}
