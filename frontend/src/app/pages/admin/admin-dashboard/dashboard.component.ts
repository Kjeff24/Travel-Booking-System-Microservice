import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category/category.service';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CategoryItem } from '../../../models/category-item';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [AdminNavbarComponent, RouterOutlet, CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  categorySize: number;
  categoryItemList!: CategoryItem[];
  
  constructor(
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {

    this.categoryService.getAllCategory().subscribe({
      next: (data: any) => {
        this.categorySize = data.body.length;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });

    this.categoryService.getAllCategory().subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
      },
      error: () => {
        console.log( "Error");
      }
    })

    // this.bookingService.getNumberofProducts().subscribe({
    //   next: (data: any) => {
    //     console.log(data.body);
    //   },
    //   error: (error: string) => {
    //     console.log(`Error: ${error}`);
    //   },
    // });
  }

  getRouterLink(code: string): any[] {
    switch (code) {
      case 'ACC':
        return ['/dashboard/category/accommodation'];
      case 'HOT':
        return ['/dashboard/category/hotel'];
      case 'CAR':
        return ['/dashboard/category/car-rental'];
      case 'FLI':
        return ['/dashboard/category/flight'];
      default:
        return ['']; 
    }
  }
}
