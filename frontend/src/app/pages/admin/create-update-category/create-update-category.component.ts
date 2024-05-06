import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryItem } from '../../../models/category-item';

@Component({
  selector: 'app-create-category',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-update-category.component.html',
  styleUrl: './create-update-category.component.css'
})
export class CreateUpdateCategoryComponent {

  isUpdate: boolean = false;
  success: boolean = false;
  successMessage: string;
  error: boolean = false;
  errorMessages: any;
  categoryItem: CategoryItem;
  categoryId: string;

  constructor(private location: Location, private categoryService: CategoryService) {
    this.categoryItem = new CategoryItem();
  }

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
      if (currentUrl.includes('update')) {
        this.isUpdate = true;
        const urlSegments = currentUrl.split('/');
        this.categoryId = urlSegments[urlSegments.length - 1];
      }
    }


  }

  handleSubmit(): void{
    if (this.isUpdate) {
      this.handleUpdate();
    } else {
      this.handleCreate();
    }
  }

  handleUpdate(): void {
    this.categoryService
      .updateCategory(this.categoryId, this.categoryItem)
      .subscribe({
        next: (response) => {
          this.categoryItem = response.body;
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
    this.categoryService
      .createCategory(this.categoryItem)
      .subscribe({
        next: (response) => {
          this.categoryItem = response.body;
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
