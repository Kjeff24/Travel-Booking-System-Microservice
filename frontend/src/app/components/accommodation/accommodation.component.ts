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
  accommodationList!: AccommodationItem[];
  filteredAccommodationList!: AccommodationItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
    this.bookingService.getAllAccommodations().subscribe({
      next: (data: AccommodationItem[]) => {
        console.log('Data received');
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
    item => item?.capacity.toLowerCase().includes(text) ||
    item?.location.toLowerCase().includes(text) ||
    item?.type.toLowerCase().includes(text)
    );
  }

  
}
