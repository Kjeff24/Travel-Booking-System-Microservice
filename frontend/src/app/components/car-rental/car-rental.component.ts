import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CarRentalItem } from '../../models/car-rental-item';
import { OrderItem } from '../../models/order-item';
import { BookingService } from '../../services/booking/booking.service';
import { CarRentalService } from '../../services/car-rental/car-rental.service';
import { CartService } from '../../services/cart/cart.service';
import { TokenService } from '../../services/token/token.service';
import { UserstateComponent } from '../userstate/userstate.component';
import { OrderService } from '../../services/order/order.service';

@Component({
  selector: 'app-car-rental',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.css'
})
export class CarRentalComponent  extends UserstateComponent {
  isUpdate: boolean = false;
  carRentalList!: CarRentalItem[];
  filteredCarRentalList!: CarRentalItem[];
  orderItem: OrderItem;

  constructor(
    private bookingService: BookingService,
    private orderService: OrderService,
    private carRentalService: CarRentalService,
    public override tokenService: TokenService,
    private cartService: CartService,
    private location: Location
  ){
    super(tokenService)
  }

  ngOnInit(): void {
    this.getLogged();
    const currentUrl = this.location.path();
    if (currentUrl.includes('dashboard')) {
      this.isUpdate = true;
    }
    this.carRentalService.getAllCarRental().subscribe({
      next: (response) => {
        this.carRentalList = response.body;
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

  addToCart(bookingId: string, price: number): void {
    if(this.isLoggedIn){
      this.orderService.addToCart({userId: this.userId, bookingId, price}).subscribe({
        next: (data:any) => {
          this.orderItem = data.body;
          window.location.reload();
        }
      })
    }else {
      this.cartService.addToCart(bookingId, price);
      window.location.reload();
    }
  }


}
