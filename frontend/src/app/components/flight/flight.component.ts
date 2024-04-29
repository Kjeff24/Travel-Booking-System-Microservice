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

  flightItemList!: FlightItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
    this.bookingService.getAllFlight().subscribe({
      next: (data: FlightItem[]) => {
        console.log('Data received');
        this.flightItemList = data;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

}
