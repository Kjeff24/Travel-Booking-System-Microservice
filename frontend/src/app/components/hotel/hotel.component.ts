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
  hotelList!: HotelItem[];
  filteredHotelList!: HotelItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
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

}
