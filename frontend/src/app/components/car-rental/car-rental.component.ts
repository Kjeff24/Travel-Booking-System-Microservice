import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CarRentalItem } from '../../models/car-rental-item';
import { BookingService } from '../../services/booking/booking.service';
import { OrderItem } from '../../models/order-item';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-car-rental',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.css'
})
export class CarRentalComponent implements  OnInit {

  carRentalList!: CarRentalItem[];
  filteredCarRentalList!: CarRentalItem[];
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
    this.bookingService.getAllCarRental().subscribe({
      next: (data: CarRentalItem[]) => {
        this.carRentalList = data;
        this.filteredCarRentalList = this.carRentalList
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  filterResults(text: string) {
    if (!text) {
      this.filteredCarRentalList = this.carRentalList;
      return;
    }
    
    text = text.toLowerCase().trim();
    this.filteredCarRentalList = this.carRentalList.filter(
    item => item?.carType.toLowerCase().includes(text)
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
