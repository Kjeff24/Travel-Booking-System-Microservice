import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryItem } from '../../../models/category-item';
import { HotelItem } from '../../../models/hotel-item';
import { CategoryService } from '../../../services/category/category.service';
import { HotelService } from '../../../services/hotel/hotel.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-create-hotel',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './create-update-hotel.component.html',
  styleUrl: './create-update-hotel.component.css'
})
export class CreateUpdateHotelComponent {
  hotelItem: HotelItem;
  isUpdate: boolean = false;
  bookingId: string;
  categoryItemUpdate: CategoryItem;
  categoryItemList!: CategoryItem[];
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;

  constructor(private location: Location, private hotelService: HotelService, private categoryService: CategoryService) {
    this.hotelItem = new HotelItem();
    this.categoryItemUpdate = new CategoryItem();
  }

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
      const urlSegments = currentUrl.split('/');
      this.bookingId = urlSegments[urlSegments.length - 1];
      this.loadHotel();
    }

    this.categoryService.getAllCategoryByCode('ACC').subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
      },
      error: () => {
        console.log('Error');
      },
    });
  }

  loadHotel(): void {
    this.hotelService.getHotelById(this.bookingId)
      .subscribe({
        next: (response) => {
          this.hotelItem = response.body;
        },
        error: (error: any) => {
          console.log(`Error: ${error}`);
        }
      });
  }

  getCategoryItem(): void {
    this.hotelService
      .getCategoryByHotelId(this.bookingId)
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
    this.hotelService
      .updateHotel(this.bookingId, this.hotelItem)
      .subscribe({
        next: (response) => {
          this.hotelItem = response.body;
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
    this.hotelService
      .createHotel(this.hotelItem)
      .subscribe({
        next: (response) => {
          this.hotelItem = response.body;
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
