import { Component, OnInit } from '@angular/core';
import { AccommodationItem } from '../../models/accommodation-item';
import { BookingService } from '../../services/booking/booking.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accommodation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './accommodation.component.html',
  styleUrl: './accommodation.component.css'
})
export class AccommodationComponent implements OnInit{
  accommodationItemList!: AccommodationItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
    this.bookingService.getAllAccommodations().subscribe({
      next: (data: AccommodationItem[]) => {
        console.log('Data received');
        this.accommodationItemList = data;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

}
