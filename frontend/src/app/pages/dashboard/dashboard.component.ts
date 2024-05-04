import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { CategoryService } from '../../services/category/category.service';
import { BookingService } from '../../services/booking/booking.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NavbarComponent],
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
