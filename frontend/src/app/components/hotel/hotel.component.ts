import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../services/booking/booking.service';
import { HotelItem } from '../../models/hotel-item';

@Component({
  selector: 'app-hotel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hotel.component.html',
  styleUrl: './hotel.component.css'
})
export class HotelComponent implements OnInit{
  hotelItemList!: HotelItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
    this.bookingService.getAllHotel().subscribe({
      next: (data: HotelItem[]) => {
        console.log('Data received');
        this.hotelItemList = data;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }
}
