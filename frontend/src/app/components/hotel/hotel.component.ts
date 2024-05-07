import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HotelItem } from '../../models/hotel-item';
import { OrderItem } from '../../models/order-item';
import { BookingService } from '../../services/booking/booking.service';
import { CartService } from '../../services/cart/cart.service';
import { HotelService } from '../../services/hotel/hotel.service';
import { OrderService } from '../../services/order/order.service';
import { TokenService } from '../../services/token/token.service';
import { UserstateComponent } from '../userstate/userstate.component';

@Component({
  selector: 'app-hotel',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './hotel.component.html',
  styleUrl: './hotel.component.css'
})
export class HotelComponent extends UserstateComponent{
  hotelList!: HotelItem[];
  filteredHotelList!: HotelItem[];
  orderItem: OrderItem;
  isDashboardPage: boolean = false;
  categoryId: string;
  categoryName: string;

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService,
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
      this.getHotels();
    });
    
  }

  getHotels(): void {
    this.hotelService.getAllHotelsByCategoryId(this.categoryId).subscribe({
      next: (response) => {
        this.hotelList = response.body;
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
