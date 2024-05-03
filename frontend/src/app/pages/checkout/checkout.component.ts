import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { BookingService } from '../../services/booking/booking.service';
import { TokenService } from '../../services/token/token.service';

declare var PaystackPop: any;

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, RouterLink, NavbarComponent, FooterComponent],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit{
  
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  userId: string;
  totalCartItems: number;
  totalCartItemsPrice: number;
  reference: string;
  title: string;
  email: string = '';
  amount: number = 0;



  constructor(
    private bookingService: BookingService,
    private tokenService: TokenService
  ){}

  ngOnInit(): void {
    this.reference = `ref-${Math.ceil(Math.random() * 10e13)}`;
    this.getLogged();
    this.getCartsTotalQuantity();
    this.getCartsTotalPrice();
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
          this.totalCartItems = data.body;
        },
      });
    }
  }

  getCartsTotalPrice(): void {
    if (this.isLoggedIn) {
      this.bookingService.getCartItemsTotalPrice(this.userId).subscribe({
        next: (data: any) => {
          this.totalCartItemsPrice = data.body;
        },
      });
    }
  }

  payWithPaystack() {
    let handler = PaystackPop.setup({
      key: 'pk_test_085824e3d504411b6e32f41420095f8899ace069', // Replace with your public key
      email: this.email,
      amount: this.amount * 100,
      ref: '' + Math.floor((Math.random() * 1000000000) + 1),
      onClose: () => {
        alert('Window closed.');
      },
      callback: (response: any) => {
        let message = 'Payment complete! Reference: ' + response.reference;
        alert(message);
      }
    });

    handler.openIframe();
  }

}
