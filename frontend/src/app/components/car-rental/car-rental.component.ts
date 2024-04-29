import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CarRentalItem } from '../../models/car-rental-item';
import { BookingService } from '../../services/booking/booking.service';

@Component({
  selector: 'app-car-rental',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.css'
})
export class CarRentalComponent implements  OnInit {

  carRentalItemList!: CarRentalItem[];

  constructor(
    private bookingService: BookingService
  ){}

  ngOnInit(): void {
    this.bookingService.getAllCarRental().subscribe({
      next: (data: CarRentalItem[]) => {
        console.log('Data received');
        this.carRentalItemList = data;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

}
