import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import {
  NavigationEnd,
  Router,
  RouterLink,
} from '@angular/router';
import { BookingService } from '../../services/booking/booking.service';
import { CategoryCardComponent } from '../../components/category-card/category-card.component';
import { CategoryItem } from '../../models/category-item';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NavbarComponent,
    FooterComponent,
    RouterLink,
    CategoryCardComponent,
    CommonModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  @ViewChild('navbar') navbar: NavbarComponent;

  categoryItemList!: CategoryItem[];
  constructor(private bookingService: BookingService, private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.navbar.getLogged();
      }})

    this.bookingService.getAllCategory().subscribe({
      next: (data: CategoryItem[]) => {
        console.log('Data received');
        this.categoryItemList = data;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }
}
