import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category/category.service';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CategoryItem } from '../../../models/category-item';
import { CommonModule } from '@angular/common';
import { BookingService } from '../../../services/booking/booking.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [AdminNavbarComponent, RouterOutlet, CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  categorySize: number;
  totalProducts: number;
  categoryItemList!: CategoryItem[];
  
  constructor(
    private categoryService: CategoryService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.getCategories();
    this.getNumberofProducts();
    
  }

  getNumberofProducts(): void {
    this.bookingService.getNumberofProducts().subscribe({
      next: (data: any) => {
        this.totalProducts = data.body
      },
      error: () => {
        console.log( "Error");
      }
    })
  }

  getCategories(): void {
    this.categoryService.getAllCategory().subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
        this.categorySize = data.body.length;
      },
      error: () => {
        console.log( "Error");
      }
    })
  }

  getRouterLink(code: string, categoryName: string, categoryId: string): any[] {
    switch (code) {
      case 'ACC':
        return ['/dashboard/category/accommodation', categoryName, categoryId];
      case 'HOT':
        return ['/dashboard/category/hotel', categoryName, categoryId];
      case 'CAR':
        return ['/dashboard/category/car-rental', categoryName, categoryId];
      case 'FLI':
        return ['/dashboard/category/flight', categoryName, categoryId];
      default:
        return ['']; 
    }
  }
}
