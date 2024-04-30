import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FlightItem } from '../../models/flight-item';
import { BookingService } from '../../services/booking/booking.service';

@Component({
  selector: 'app-flight',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './flight.component.html',
  styleUrl: './flight.component.css'
})
export class FlightComponent {

  flightList!: FlightItem[];
  filteredFlightList!: FlightItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
    this.bookingService.getAllFlight().subscribe({
      next: (data: FlightItem[]) => {
        console.log('Data received');
        this.flightList = data;
        this.filteredFlightList = this.flightList;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  filterResults(text: string) {
    if (!text) {
      this.filteredFlightList = this.flightList;
      return;
    }
    
    text = text.toLowerCase().trim();
    this.filteredFlightList = this.flightList.filter(
    item => item?.departureCity.toLowerCase().includes(text) ||
    item?.destinationCity.toLowerCase().includes(text)
    );
  }


}
