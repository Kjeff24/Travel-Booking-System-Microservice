import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category/category.service';
import { BookingService } from '../../../services/booking/booking.service';
import { AdminNavbarComponent } from '../admin-navbar/admin-navbar.component';
import { CreateUpdateAccommodationComponent } from '../create-update-accommodation/create-update-accommodation.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [AdminNavbarComponent, RouterOutlet],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  categorySize: number;
  constructor(
    private categoryService: CategoryService,
    private bookingService: BookingService
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

    // this.bookingService.getNumberofProducts().subscribe({
    //   next: (data: any) => {
    //     console.log(data.body);
    //   },
    //   error: (error: string) => {
    //     console.log(`Error: ${error}`);
    //   },
    // });
  }
}
