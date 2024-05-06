import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { CarRentalItem } from '../../../models/car-rental-item';
import { CarRentalService } from '../../../services/car-rental/car-rental.service';
import { FormsModule } from '@angular/forms';
import { CategoryItem } from '../../../models/category-item';
import { CategoryService } from '../../../services/category/category.service';

@Component({
  selector: 'app-create-car-rental',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-update-car-rental.component.html',
  styleUrl: './create-update-car-rental.component.css'
})
export class CreateUpdateCarRentalComponent {
  bookingId: string;
  isUpdate: boolean = false;
  categoryItemUpdate: CategoryItem;
  carRentalItem: CarRentalItem;
  categoryItemList!: CategoryItem[];
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;

  constructor(
    private location: Location, 
    private carRentalService: CarRentalService,
    private categoryService: CategoryService,
  ) {
    this.categoryItemUpdate = new CategoryItem();
    this.carRentalItem = new CarRentalItem();
  }

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
      const urlSegments = currentUrl.split('/');
      this.bookingId = urlSegments[urlSegments.length - 1];
      this.loadCarRental();
    }


    this.categoryService.getAllCategoryByCode('CAR').subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
      },
      error: () => {
        console.log('Error');
      },
    });
  }

  loadCarRental(): void {
    this.carRentalService.getCarRentalById(this.bookingId)
      .subscribe({
        next: (response) => {
          this.carRentalItem = response.body;
        },
        error: (error: any) => {
          console.log(`Error: ${error}`);
        }
      });
  }

  getCategoryItem(): void {
    this.carRentalService
      .getCategoryByCarRentalId(this.bookingId)
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
    this.carRentalService
      .updateCarRental(this.bookingId, this.carRentalItem)
      .subscribe({
        next: (response) => {
          this.carRentalItem = response.body;
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
    this.carRentalService
      .createCarRental(this.carRentalItem)
      .subscribe({
        next: (response) => {
          this.carRentalItem = response.body;
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
