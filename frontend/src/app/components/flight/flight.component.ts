import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FlightItem } from '../../models/flight-item';
import { BookingService } from '../../services/booking/booking.service';
import { TokenService } from '../../services/token/token.service';
import { OrderItem } from '../../models/order-item';
import { producerNotifyConsumers } from '@angular/core/primitives/signals';
import { CartService } from '../../services/cart/cart.service';
import { UserstateComponent } from '../userstate/userstate.component';

@Component({
  selector: 'app-flight',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './flight.component.html',
  styleUrl: './flight.component.css'
})
export class FlightComponent  extends UserstateComponent{

  flightList!: FlightItem[];
  filteredFlightList!: FlightItem[];
  orderItem: OrderItem;

  constructor(
    private bookingService: BookingService,
    public override tokenService: TokenService,
    private cartService: CartService
  ){
    super(tokenService)
  }

  ngOnInit(): void {
    this.getLogged();
    this.bookingService.getAllFlight().subscribe({
      next: (data: FlightItem[]) => {
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

  addToCart(bookingId: string, price: number): void {
    if(this.isLoggedIn){
      this.bookingService.addToCart({userId: this.userId, bookingId, producerNotifyConsumers}).subscribe({
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
