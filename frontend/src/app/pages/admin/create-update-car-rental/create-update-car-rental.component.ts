import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { CarRentalItem } from '../../../models/car-rental-item';
import { CarRentalService } from '../../../services/car-rental/car-rental.service';
import { FormsModule } from '@angular/forms';
import { CategoryItem } from '../../../models/category-item';
import { CategoryService } from '../../../services/category/category.service';
import { RouterLink } from '@angular/router';
import { FileHandle } from '../../../models/file-handle';
import { DomSanitizer } from '@angular/platform-browser';
import { map } from 'rxjs';
import { ImageProcessingService } from '../../../services/image-processing/image-processing.service';

@Component({
  selector: 'app-create-car-rental',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './create-update-car-rental.component.html',
  styleUrl: './create-update-car-rental.component.css',
})
export class CreateUpdateCarRentalComponent {
  bookingId: string;
  isUpdate: boolean = false;
  categoryItemUpdate: CategoryItem;
  carRentalItem!: CarRentalItem;
  categoryItemList!: CategoryItem[];
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;

  constructor(
    private location: Location,
    private carRentalService: CarRentalService,
    private categoryService: CategoryService,
    private sanitizer: DomSanitizer,
    private imageProcessingService: ImageProcessingService
  ) {
    this.carRentalItem = new CarRentalItem();
    this.categoryItemUpdate = new CategoryItem();
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
    .pipe(
      map((product: CarRentalItem, i) => this.imageProcessingService.createCarRentalImage(product))
    )
    .subscribe({
      next: (response) => {
        this.carRentalItem = response;
        this.getCategoryItem(this.carRentalItem.categoryId);
      },
      error: (error: any) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  getCategoryItem(categoryId: string): void {
    this.categoryService.getCategoryById(categoryId)
    .subscribe({
      next: (response) => {
        this.categoryItemUpdate = response;
      },
      error: (err) => {
        console.log(err);
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
    const carRentalFormData: any = this.prepareFormData(this.carRentalItem);
    if (carRentalFormData != null) {
      this.carRentalService
        .updateCarRental(this.bookingId, carRentalFormData)
        .subscribe({
          next: (response) => {
            this.carRentalItem = response.body;
            this.success = true;
            this.successMessage = 'Product Updated Successfully';
          },
          error: (error: any) => {
            if (error.status === 400) {
              this.error = true;
              this.errorMessages = Object.values(error.error);
            }
          },
        });
    }
  }

  handleCreate(): void {
    const carRentalFormData: any = this.prepareFormData(this.carRentalItem);
    if (carRentalFormData != null) {
      this.carRentalService.createCarRental(carRentalFormData).subscribe({
        next: (response) => {
          this.carRentalItem = response.body;
          this.success = true;
          this.successMessage = 'Product Added Successfully';
          this.carRentalItem = new CarRentalItem();
        },
        error: (error: any) => {
          if (error.status === 400) {
            this.error = true;
            this.errorMessages = Object.values(error.error);
          }
        },
      });
    }
  }

  prepareFormData(carRentalItem: CarRentalItem): FormData {
    const formData: any = new FormData();
    formData.append(
      'carRentalRequest',
      new Blob([JSON.stringify(carRentalItem)], { type: 'application/json' })
    );
    if (carRentalItem.carImage) {
      formData.append(
        'imageFile',
        carRentalItem.carImage.file,
        carRentalItem.carImage.file.name
      );
      return formData;
    } else {
      this.error = true;
      this.errorMessages = ['Upload an Image'];
      return null;
    }
  }

  onFileSelected(event) {
    if (event.target.files) {
      const file = event.target.files[0];
      const fileHandle: FileHandle = {
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        ),
      };
      this.carRentalItem.carImage = fileHandle;
    }
  }
}
