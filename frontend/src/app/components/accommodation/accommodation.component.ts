import { Component } from '@angular/core';
import { AccommodationItem } from '../../models/accommodation-item';
import { BookingService } from '../../services/booking/booking.service';
import { CommonModule, Location } from '@angular/common';
import { TokenService } from '../../services/token/token.service';
import { OrderItem } from '../../models/order-item';
import { CartService } from '../../services/cart/cart.service';
import { UserstateComponent } from '../userstate/userstate.component';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AccommodationService } from '../../services/accommodation/accommodation.service';
import { OrderService } from '../../services/order/order.service';

@Component({
  selector: 'app-accommodation',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './accommodation.component.html',
  styleUrl: './accommodation.component.css',
})
export class AccommodationComponent extends UserstateComponent {
  accommodationList!: AccommodationItem[];
  filteredAccommodationList!: AccommodationItem[];
  orderItem: OrderItem;
  isDashboardPage: boolean = false;
  categoryId: string;
  categoryName: string;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private accommodationService: AccommodationService,
    public override tokenService: TokenService,
    private cartService: CartService,
    private location: Location
  ) {
    super(tokenService);
  }

  ngOnInit(): void {
    this.getLogged();
    this.route.params.subscribe((params) => {
      this.categoryId = params['id'];
      this.categoryName = params['name'];
      this.isDashboardPage = this.location.path().includes('dashboard');
      this.getAccommodations();
    });
  }

  getAccommodations(): void {
    this.accommodationService
      .getAllAccommodationsByCategoryId(this.categoryId)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.accommodationList = response.body;
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
      (item) =>
        item?.capacity.toLowerCase().includes(text) ||
        item?.location.toLowerCase().includes(text) ||
        item?.type.toLowerCase().includes(text)
    );
  }

  addToCart(bookingId: string, price: number): void {
    if (this.isLoggedIn) {
      this.orderService
        .addToCart({ userId: this.userId, bookingId, price })
        .subscribe({
          next: (data: any) => {
            this.orderItem = data.body;
            window.location.reload();
          },
        });
    } else {
      this.cartService.addToCart(bookingId, price);
      window.location.reload();
    }
  }
}
