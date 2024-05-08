import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryItem } from '../../../models/category-item';
import { FormsModule } from '@angular/forms';
import { FileHandle } from '../../../models/file-handle';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-create-category',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './create-update-category.component.html',
  styleUrl: './create-update-category.component.css',
})
export class CreateUpdateCategoryComponent {
  isUpdate: boolean = false;
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;
  categoryItem: CategoryItem;
  categoryId: string;

  constructor(
    private location: Location,
    private categoryService: CategoryService,
    private sanitizer: DomSanitizer
  ) {
    this.categoryItem = new CategoryItem();
  }

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
      const urlSegments = currentUrl.split('/');
      this.categoryId = urlSegments[urlSegments.length - 1];
      this.loadCategory();
    }
  }

  loadCategory(): void {
    this.categoryService.getCategoryById(this.categoryId).subscribe({
      next: (response) => {
        this.categoryItem = response.body;
      },
      error: (error: any) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  handleSubmit(): void {
    if (this.isUpdate) {
      this.handleUpdate();
    } else {
      this.handleCreate();
    }
  }

  handleUpdate(): void {
    const categoryFormData: any = this.prepareFormData(this.categoryItem);
    if (categoryFormData != null) {
      this.categoryService
        .updateCategory(this.categoryId, this.categoryItem)
        .subscribe({
          next: (response) => {
            this.categoryItem = response.body;
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
    const categoryFormData: any = this.prepareFormData(this.categoryItem);
    {
      if (categoryFormData != null)
        this.categoryService.createCategory(this.categoryItem).subscribe({
          next: (response) => {
            this.categoryItem = response.body;
            this.success = true;
            this.successMessage = 'Product Added Successfully';
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

  prepareFormData(categoryItem: CategoryItem): FormData {
    const formData: any = new FormData();
    formData.append(
      'carRentalRequest',
      new Blob([JSON.stringify(categoryItem)], { type: 'application/json' })
    );
    if (categoryItem.icon) {
      formData.append(
        'imageFile',
        categoryItem.icon.file,
        categoryItem.icon.file.name
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
      this.categoryItem.icon = fileHandle;
    }
  }
}
