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
import { CategoryService } from '../../services/category/category.service';

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
  accomodationId!: string;
  constructor(
    private categoryService: CategoryService, 
    private router: Router
  ) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.navbar.getLogged();
      }})

    this.categoryService.getAllCategory().subscribe({
      next: (data: CategoryItem[]) => {
        console.log('Data received');
        this.categoryItemList = data;
        this.accomodationId = this.categoryItemList[0].id;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  
}
