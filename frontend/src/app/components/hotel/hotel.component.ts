import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../services/booking/booking.service';
import { HotelItem } from '../../models/hotel-item';
import { OrderItem } from '../../models/order-item';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-hotel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hotel.component.html',
  styleUrl: './hotel.component.css'
})
export class HotelComponent implements OnInit{
  hotelList!: HotelItem[];
  filteredHotelList!: HotelItem[];
  isLoggedIn: boolean;
  isAdmin: boolean;
  isCustomer: boolean;
  userId: string;
  orderItem: OrderItem;

  constructor(
    private bookingService: BookingService,
    private tokenService: TokenService
  ){}

  ngOnInit(): void {
    this.getLogged();
    this.bookingService.getAllHotel().subscribe({
      next: (data: HotelItem[]) => {
        console.log('Data received');
        this.hotelList = data;
        this.filteredHotelList = this.hotelList;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  getLogged(): void {
    this.isLoggedIn = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isCustomer = this.tokenService.isCustomer();
    this.userId = this.tokenService.getUserId();
  }

  filterResults(text: string) {
    if (!text) {
      this.filteredHotelList = this.hotelList;
      return;
    }
    
    text = text.toLowerCase().trim();
    this.filteredHotelList = this.hotelList.filter(
    item => item?.hotelName.toLowerCase().includes(text) ||
    item?.location.toLowerCase().includes(text) ||
    item?.roomType.toLowerCase().includes(text)
    );
  }

  addToCart(bookingId: string): void {
    if(this.isLoggedIn){
      this.bookingService.addToCart({userId: this.userId, bookingId}).subscribe({
        next: (data:any) => {
          console.log(data.body)
          this.orderItem = data.body
        }
      })
    }
  }

}
