import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { producerNotifyConsumers } from '@angular/core/primitives/signals';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FlightItem } from '../../models/flight-item';
import { OrderItem } from '../../models/order-item';
import { BookingService } from '../../services/booking/booking.service';
import { CartService } from '../../services/cart/cart.service';
import { FlightService } from '../../services/flight/flight.service';
import { OrderService } from '../../services/order/order.service';
import { TokenService } from '../../services/token/token.service';
import { UserstateComponent } from '../userstate/userstate.component';

@Component({
  selector: 'app-flight',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './flight.component.html',
  styleUrl: './flight.component.css'
})
export class FlightComponent  extends UserstateComponent{
  flightList!: FlightItem[];
  filteredFlightList!: FlightItem[];
  orderItem: OrderItem;
  isDashboardPage: boolean = false;
  categoryId: string;
  categoryName: string;

  constructor(
    private route: ActivatedRoute,
    private flightService: FlightService,
    private orderService: OrderService,
    public override tokenService: TokenService,
    private cartService: CartService,
    private location: Location
  ){
    super(tokenService)
  }

  ngOnInit(): void {
    this.getLogged();
    this.route.params.subscribe((params) => {
      this.categoryId = params['id'];
      this.categoryName = params['name'];
      this.isDashboardPage = this.location.path().includes('dashboard');
      this.getFlights();
    });
  }

  getFlights(): void {
    this.flightService.getAllFlightsByCategoryId(this.categoryId).subscribe({
      next: (response) => {
        this.flightList = response.body;
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
      this.orderService.addToCart({userId: this.userId, bookingId, producerNotifyConsumers}).subscribe({
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
