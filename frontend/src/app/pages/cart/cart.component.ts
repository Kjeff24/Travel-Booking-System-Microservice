import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { forkJoin, map } from 'rxjs';
import { FooterComponent } from '../../components/footer/footer.component';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { UserstateComponent } from '../../components/userstate/userstate.component';
import { AccommodationCartItem } from '../../models/accommodation-cart-item';
import { CarRentalCartItem } from '../../models/car-rental-cart-item';
import { FlightCartItem } from '../../models/flight-cart-item';
import { HotelCartItem } from '../../models/hotel-cart-item';
import { BookingService } from '../../services/booking/booking.service';
import { CartService } from '../../services/cart/cart.service';
import { CategoryService } from '../../services/category/category.service';
import { TokenService } from '../../services/token/token.service';
import { OrderService } from '../../services/order/order.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [NavbarComponent, FooterComponent, RouterLink, CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent extends UserstateComponent {
  accommodationCartItemList: AccommodationCartItem[] = [];
  flightCartItemList: FlightCartItem[] = [];
  carRentalCartItemList: CarRentalCartItem[] = [];
  hotelCartItemList: HotelCartItem[] = [];
  totalCartItems: number;
  totalCartItemsPrice: number;

  constructor(
    private bookingService: BookingService,
    private categoryService: CategoryService,
    private orderService: OrderService,
    private cartService: CartService,
    public override tokenService: TokenService
  ) {
    super(tokenService);
  }

  ngOnInit(): void {
    this.getLogged();
    this.getCartsTotalQuantity();
    this.getCartsTotalPrice();
    if (this.isLoggedIn) {
      this.orderService.getCartItems(this.userId).subscribe({
        next: (data: any) => {
          this.filterCartItems(data.body);
        },
        error: (error: string) => {
          console.log(`Error: ${error}`);
        },
      });
    } else {
      this.filterCartItems(this.cartService.getCartItems());
    }
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
    this.categoryService
      .getCategoryById(item.categoryId)
      .subscribe((categoryResponse: any) => {
        const category = categoryResponse;
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

  increaseCartItem(bookingId: string, price: number): void {
    if (this.isLoggedIn) {
      this.orderService
        .addToCart({ userId: this.userId, bookingId, price })
        .subscribe({
          next: () => {
            window.location.reload();
          },
        });
    } else {
      this.cartService.addToCart(bookingId, price);
      window.location.reload();
    }
  }

  decreaseCartItem(bookingId: string, price: number): void {
    if (this.isLoggedIn) {
      this.orderService
        .decreaseCartItem({ userId: this.userId, bookingId, price })
        .subscribe({
          next: () => {
            window.location.reload();
          },
        });
    }else {
      this.cartService.decreaseCartItem(bookingId, price);
      window.location.reload();
    }
  }

  removeCartItem(bookingId: string): void {
    if (this.isLoggedIn) {
      this.orderService.removeCartItem(this.userId, bookingId).subscribe({
        next: () => {
          window.location.reload();
        },
      });
    }else {
      this.cartService.deleteCartItem(bookingId);
      window.location.reload();
    }
  }
}
