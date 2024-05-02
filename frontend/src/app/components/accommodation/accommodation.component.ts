import { Component, OnInit } from '@angular/core';
import { AccommodationItem } from '../../models/accommodation-item';
import { BookingService } from '../../services/booking/booking.service';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../services/token/token.service';
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

  addToCart(bookingId: string): void {
    if(this.isLoggedIn){
      this.bookingService.addToCart({userId: this.userId, bookingId}).subscribe({
        next: (data:any) => {
          this.orderItem = data.body;
          window.location.reload();
        }
      })
    }
  }
}
