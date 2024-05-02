import { Component, OnInit } from '@angular/core';
import { AccommodationItem } from '../../models/accommodation-item';
import { BookingService } from '../../services/booking/booking.service';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../services/token/token.service';
import { Order } from '../../models/order';
import { OrderItem } from '../../models/order-item';

@Component({
  selector: 'app-accommodation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './accommodation.component.html',
  styleUrl: './accommodation.component.css',
})
export class AccommodationComponent implements OnInit {
  accommodationList!: AccommodationItem[];
  filteredAccommodationList!: AccommodationItem[];
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  userId: string;
  order: Order;
  orderItem: OrderItem;

  constructor(
    private bookingService: BookingService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.getLogged();
    this.bookingService.getAllAccommodations().subscribe({
      next: (data: AccommodationItem[]) => {
        this.accommodationList = data;
        this.filteredAccommodationList = this.accommodationList;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  filterResults(text: string) {
    if (!text) {
      this.filteredAccommodationList = this.accommodationList;
      return;
    }

    text = text.toLowerCase().trim();
    this.filteredAccommodationList = this.accommodationList.filter(
      (item) =>
        item?.capacity.toLowerCase().includes(text) ||
        item?.location.toLowerCase().includes(text) ||
        item?.type.toLowerCase().includes(text)
    );
  }

  getLogged(): void {
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isCustomer = this.tokenService.isCustomer();
    this.userId = this.tokenService.getUserId();
  }

  addItemToCart(bookingId: string): void {
    if (!this.orderItem) {
      this.addOrderItem(bookingId);
      return;
    }
    this.bookingService.findOrderItemById(this.orderItem.id).subscribe({
      next: (data: OrderItem) => {
        const updatedQuantity = this.orderItem.quantity + 1;
        this.updateOrderItem(updatedQuantity);
      },
      error: (error: any) => {
        if (error.status === 404) {
          this.addOrderItem(bookingId);
        }
      },
    });
  }

  updateOrderItem(updatedQuantity: number): void {
    this.bookingService
      .updateOrderItem(this.orderItem.id, { quantity: updatedQuantity })
      .subscribe({
        next: (newdata: any) => {
          this.orderItem = newdata.body;
        },
        error: (error: string) => {
          console.log('failed to update');
        },
      });
  }

  addOrderItem(bookingId: string): void {
    console.log('Hello from addOrderItem');
    this.bookingService
      .addOrderItem({
        bookingId: bookingId,
        quantity: 1,
        orderId: this.order.id,
      })
      .subscribe({
        next: (data) => {
          console.log('Booking added to cart');
          this.orderItem = data.body;
          console.log(this.orderItem);
        },
        error: (e: string) => {
          console.log(`Failed to add the booking`);
        },
      });
  }

  createOrder(bookingId: string): void {
    if (this.isLoggedIn) {
      console.log(this.userId);
      this.bookingService.findOrderByUserId(this.userId).subscribe({
        next: (data: any) => {
          console.log('Orders found:', data.body);
          this.order = data.body;
          console.log('id oo');
          console.log(this.order.id);
          this.addItemToCart(bookingId);
        },
        error: (error: any) => {
          if (error.status === 404) {
            console.log('No order found, creating new order...');
            this.bookingService.createOrder({ userId: this.userId }).subscribe({
              next: (data: any) => {
                console.log('New order created:', data.body);
                this.order = data.body;
                console.log('id oo');
                console.log(data.body.id);
                this.addItemToCart(bookingId);
              },
              error: (err: any) => {
                console.log('Error creating new order:', err);
              },
            });
          } else {
            console.log('Error finding order:', error);
          }
        },
      });
    }
  }
}
