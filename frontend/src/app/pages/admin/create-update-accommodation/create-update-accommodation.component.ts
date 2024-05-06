import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormsModule } from '@angular/forms';
import { AccommodationItem } from '../../../models/accommodation-item';
import { AccommodationService } from '../../../services/accommodation/accommodation.service';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryItem } from '../../../models/category-item';

@Component({
  selector: 'app-create-accommodation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-update-accommodation.component.html',
  styleUrl: './create-update-accommodation.component.css',
})
export class CreateUpdateAccommodationComponent {
  isUpdate: boolean = false;
  bookingId: string;
  categoryItemUpdate: CategoryItem;
  accommodationItem: AccommodationItem;
  categoryItemList!: CategoryItem[];
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;

  constructor(
    private location: Location,
    private accommodationService: AccommodationService,
    private categoryService: CategoryService
  ) {
    this.accommodationItem = new AccommodationItem();
    this.categoryItemUpdate = new CategoryItem();
  }

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
      const urlSegments = currentUrl.split('/');
      this.bookingId = urlSegments[urlSegments.length - 1];
      this.loadAccommodation();
      this.getCategoryItem();
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

  loadAccommodation(): void {
    this.accommodationService.getAccommodationById(this.bookingId).subscribe({
      next: (response) => {
        this.accommodationItem = response.body;
      },
      error: (error: any) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  getCategoryItem(): void {
    this.accommodationService
      .getCategoryByAccommodationId(this.bookingId)
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
    this.accommodationService
      .updateAccommodation(this.bookingId, this.accommodationItem)
      .subscribe({
        next: (response) => {
          this.accommodationItem = response.body;
          this.success = true;
          this.successMessage = 'Product Updated Successfully';
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
    this.accommodationService
      .createAccommodation(this.accommodationItem)
      .subscribe({
        next: (response) => {
          this.accommodationItem = response.body;
          this.success = true;
          this.successMessage = 'Product Added Successfully';
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
