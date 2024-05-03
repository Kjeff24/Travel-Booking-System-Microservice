import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { RouterLink } from '@angular/router';
import { BookingService } from '../../services/booking/booking.service';
import { TokenService } from '../../services/token/token.service';
import { forkJoin, map } from 'rxjs';
import { CommonModule } from '@angular/common';
import { AccommodationCartItem } from '../../models/accommodation-cart-item';
import { FlightCartItem } from '../../models/flight-cart-item';
import { CarRentalCartItem } from '../../models/car-rental-cart-item';
import { HotelCartItem } from '../../models/hotel-cart-item';
import { CartService } from '../../services/cart/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [NavbarComponent, FooterComponent, RouterLink, CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit {
  accommodationCartItemList: AccommodationCartItem[] = [];
  flightCartItemList: FlightCartItem[] = [];
  carRentalCartItemList: CarRentalCartItem[] = [];
  hotelCartItemList: HotelCartItem[] = [];
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  userId: string;
  totalCartItems: number;
  totalCartItemsPrice: number;

  constructor(
    private bookingService: BookingService,
    private tokenService: TokenService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getLogged();
    this.getCartsTotalQuantity();
    this.getCartsTotalPrice();
    if (this.isLoggedIn) {
      this.bookingService.getCartItems(this.userId).subscribe({
        next: (data: any) => {
          this.filterCartItems(data.body);
        },
        error: (error: string) => {
          console.log(`Error: ${error}`);
        },
      });
    } else {
      this.filterCartItems(this.cartService.getCartItems())
    }
  }

  getLogged(): void {
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isCustomer = this.tokenService.isCustomer();
    this.userId = this.tokenService.getUserId();
  }

  filterCartItems(cartItems: any[]): void {
    const observables = cartItems.map((item: any) => {
      const productObservable = this.bookingService.getProductByBookingOfferId(
        item.bookingId
      );
      const quantity = item.quantity;
      return productObservable.pipe(
        map((result: any) => ({ body: result.body, quantity: quantity }))
      );
    });

    forkJoin(observables).subscribe({
      next: (results: any[]) => {
        results.forEach((result: any) => {
          this.processItemByCategory(result.body, result.quantity);
        });
      },
      error: (error: string) => {
        console.log(`Error fetching booking objects: ${error}`);
      },
    });
  }

  processItemByCategory(item: any, quantity: number): void {
    this.bookingService
      .getCategoryById(item.categoryId)
      .subscribe((categoryResponse: any) => {
        const category = categoryResponse.body;
        switch (category.code) {
          case 'ACC':
            this.accommodationCartItemList.push({
              ...item,
              quantity: quantity,
              totalPrice: item.price * quantity,
            });
            break;
          case 'FLI':
            this.flightCartItemList.push({
              ...item,
              quantity: quantity,
              totalPrice: item.price * quantity,
            });
            break;
          case 'CAR':
            this.carRentalCartItemList.push({
              ...item,
              quantity: quantity,
              totalPrice: item.price * quantity,
            });
            console.log(this.carRentalCartItemList);
            break;
          case 'HOT':
            this.hotelCartItemList.push({
              ...item,
              quantity: quantity,
              totalPrice: item.price * quantity,
            });
            break;
          default:
            break;
        }
      });
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

  increaseCartItem(bookingId: string, price: number): void {
    if (this.isLoggedIn) {
      this.bookingService
        .addToCart({ userId: this.userId, bookingId, price })
        .subscribe({
          next: () => {
            window.location.reload();
          },
        });
    }
  }

  decreaseCartItem(bookingId: string, price: number): void {
    if (this.isLoggedIn) {
      this.bookingService
        .decreaseCartItem({ userId: this.userId, bookingId, price })
        .subscribe({
          next: () => {
            window.location.reload();
          },
        });
    }
  }

  removeCartItem(bookingId: string): void {
    if (this.isLoggedIn) {
      this.bookingService.removeCartItem(this.userId, bookingId).subscribe({
        next: () => {
          window.location.reload();
        },
      });
    }
  }
}
