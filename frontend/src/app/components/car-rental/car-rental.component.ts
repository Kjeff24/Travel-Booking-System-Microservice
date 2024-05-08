import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CarRentalItem } from '../../models/car-rental-item';
import { CartItem } from '../../models/cart-item';
import { BookingService } from '../../services/booking/booking.service';
import { CarRentalService } from '../../services/car-rental/car-rental.service';
import { CartService } from '../../services/cart/cart.service';
import { TokenService } from '../../services/token/token.service';
import { UserstateComponent } from '../userstate/userstate.component';
import { OrderService } from '../../services/order/order.service';
import { map } from 'rxjs';
import { ImageProcessingService } from '../../services/image-processing/image-processing.service';

@Component({
  selector: 'app-car-rental',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.css'
})
export class CarRentalComponent  extends UserstateComponent {
  carRentalList!: CarRentalItem[];
  filteredCarRentalList!: CarRentalItem[];
  cartItem: CartItem;
  isDashboardPage: boolean = false;
  categoryId: string;
  categoryName: string;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private carRentalService: CarRentalService,
    public override tokenService: TokenService,
    private cartService: CartService,
    private location: Location,
    private imageProcessingService: ImageProcessingService
  ){
    super(tokenService)
  }

  ngOnInit(): void {
    this.getLogged();
    this.route.params.subscribe((params) => {
      this.categoryId = params['id'];
      this.categoryName = params['name'];
      this.isDashboardPage = this.location.path().includes('dashboard');
      this.getCarRentals();
    });
  }

  getCarRentals(): void {
    this.carRentalService.getAllCarRentalByCategoryId(this.categoryId)
    .pipe(
      map((x: CarRentalItem[], i) => x.map((product: CarRentalItem) => this.imageProcessingService.createCarRentalImage(product)))
    )
    .subscribe({
      next: (response) => {
        this.carRentalList = response;
        console.log(response)
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
          this.cartItem = data.body;
          window.location.reload();
        }
      })
    }else {
      this.cartService.addToCart(bookingId, price);
      window.location.reload();
    }
  }


}
