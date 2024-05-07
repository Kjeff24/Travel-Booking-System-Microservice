import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FlightItem } from '../../../models/flight-item';
import { FlightService } from '../../../services/flight/flight.service';
import { FormsModule } from '@angular/forms';
import { CategoryItem } from '../../../models/category-item';
import { CategoryService } from '../../../services/category/category.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-create-flight',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './create-update-flight.component.html',
  styleUrl: './create-update-flight.component.css'
})
export class CreateUpdateFlightComponent {
  flightItem: FlightItem;
  isUpdate: boolean = false;
  bookingId: string;
  categoryItemUpdate: CategoryItem;
  categoryItemList!: CategoryItem[];
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;

  constructor(private location: Location, private flightService: FlightService, private categoryService: CategoryService) {
    this.flightItem = new FlightItem();
    this.categoryItemUpdate = new CategoryItem();
  }

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
      const urlSegments = currentUrl.split('/');
      this.bookingId = urlSegments[urlSegments.length - 1];
      this.loadFlight();
    }


    this.categoryService.getAllCategoryByCode('FLI').subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
      },
      error: () => {
        console.log('Error');
      },
    });
  }

  loadFlight(): void {
    this.flightService.getFlightById(this.bookingId)
      .subscribe({
        next: (response) => {
          this.flightItem = response.body;
        },
        error: (error: any) => {
          console.log(`Error: ${error}`);
        }
      });
  }

  getCategoryItem(): void {
    this.flightService
      .getCategoryByFlightId(this.bookingId)
      .subscribe({
        next: (response) => {
          this.categoryItemUpdate = response.body;
        },
        error: (error: any) => {
          console.log(`Error: ${error}`);
        },
      });
  }

  handleSubmit() {
    if (this.isUpdate) {
      this.handleUpdate();
    } else {
      this.handleCreate();
    }
  }

  handleUpdate(): void {
    this.flightService
      .updateFlight(this.bookingId, this.flightItem)
      .subscribe({
        next: (response) => {
          this.flightItem = response.body;
          this.success = true;
          this.successMessage = "Product Updated Successfully"
        },
        error: (error: any) => {
          if (error.status === 400) {
            this.error = true
            this.errorMessages = Object.values(error.error);
          }
        },
      });
  }

  handleCreate(): void {
    console.log(this.flightItem)
    this.flightService
      .createFlight(this.flightItem)
      .subscribe({
        next: (response) => {
          this.flightItem = response.body;
          this.success = true;
          this.successMessage = "Product Added Successfully"
        },
        error: (error: any) => {
          if (error.status === 400) {
            this.error = true
            this.errorMessages = Object.values(error.error);
          }
        },
      });
  }
}
