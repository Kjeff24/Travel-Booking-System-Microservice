import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FooterComponent } from '../../components/footer/footer.component';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { UserstateComponent } from '../../components/userstate/userstate.component';
import { CartService } from '../../services/cart/cart.service';
import { OrderService } from '../../services/order/order.service';
import { TokenService } from '../../services/token/token.service';
import { PaymentService } from '../../services/payment/payment.service';
import { PaymentItem } from '../../models/payment-item';
import { CartItem } from '../../models/cart-item';
import { retryWhen } from 'rxjs';


@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, RouterLink, NavbarComponent, FooterComponent],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css',
})
export class CheckoutComponent extends UserstateComponent {
  totalCartItems: number;
  totalCartItemsPrice: number;
  title: string;
  email: string = '';
  amount: number = 0;
  handler: any = null;
  paymentItem: PaymentItem;
  cartItemList: CartItem[];

  constructor(
    private orderService: OrderService,
    public override tokenService: TokenService,
    private cartService: CartService,
    private paymentService: PaymentService
  ) {
    super(tokenService);
    this.paymentItem = new PaymentItem();
  }

  ngOnInit(): void {
    this.getLogged();
    this.getCartsTotalQuantity();
    this.getCartsTotalPrice();
    this.getUserCartItems();
    this.loadStripe();
  }

  getCartsTotalQuantity(): void {
    if (this.isLoggedIn) {
      this.orderService.getCartsTotalQuantity(this.userId).subscribe({
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
      this.orderService.getCartItemsTotalPrice(this.userId).subscribe({
        next: (data: any) => {
          this.totalCartItemsPrice = data.body;
        },
      });
    } else {
      this.totalCartItemsPrice = this.cartService.getTotalPrice();
    }
  }

  getUserCartItems(): void {
    this.orderService.getCartItems(this.userId).subscribe({
      next: (response) => {
        this.cartItemList = response.body;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  sendPayments(): void {
    this.paymentService.makePayment(this.paymentItem).subscribe({
      next: (response) => {
        if(response.ok){
          this.orderService.deleteAllCartItems(this.userId).subscribe({
            next: ()=>{
              window.location.reload();
            }
          })
        }
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    })
  }

  pay() {
    var handler = (<any>window).StripeCheckout.configure({
      key: 'pk_test_51PDAy7P1Hc5vPjyRwbcj3e25f9I9TfILnpcJWhUY9vqCZLxwzCnfdPZvuqu4bzoMfKFuizFIUi61aNPDIq75Nkto00q9ry7lOl',
      locale: 'auto',
      token: (token: any) => { 
        this.paymentItem.paymentId = token['card'].id;
        this.paymentItem.email = token['card'].name;
        this.paymentItem.exp_month = token['card'].exp_month;
        this.paymentItem.exp_year = token['card'].exp_year;
        this.paymentItem.last4 = token['card'].last4;
        this.paymentItem.totalCost = this.totalCartItemsPrice;
        this.paymentItem.userId = this.userId;
        this.paymentItem.cartItems = this.cartItemList;
        this.sendPayments();
        alert('Payment Successful');
      },
    });
    
    handler.open({
      name: 'Travel Booking System',
      description: 'Pay for your items',
      amount: this.totalCartItemsPrice * 100,
    });

  }

  loadStripe() {
    if (!window.document.getElementById('stripe-script')) {
      var s = window.document.createElement('script');
      s.id = 'stripe-script';
      s.type = 'text/javascript';
      // s.src = 'https://js.stripe.com/v3';
      s.src = 'https://checkout.stripe.com/checkout.js';
      s.onload = () => {
        this.handler = (<any>window).StripeCheckout.configure({
          key: 'pk_test_51PDAy7P1Hc5vPjyRwbcj3e25f9I9TfILnpcJWhUY9vqCZLxwzCnfdPZvuqu4bzoMfKFuizFIUi61aNPDIq75Nkto00q9ry7lOl',
          locale: 'auto',
          token: function (token: any) {
            console.log(token);
            alert('Payment Success!!');
          },
        });
      };

      window.document.body.appendChild(s);
    }
  }

}