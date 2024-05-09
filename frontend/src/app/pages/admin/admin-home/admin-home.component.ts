import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CategoryCardComponent } from '../../../components/category-card/category-card.component';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryItem } from '../../../models/category-item';
import { RouterLink } from '@angular/router';
import { map } from 'rxjs';
import { ImageProcessingService } from '../../../services/image-processing/image-processing.service';

@Component({
  selector: 'app-admin-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css'
})
export class AdminHomeComponent {
  categoryItemList: CategoryItem[];

  constructor(
    private categoryService: CategoryService,
    private imageProcessingService: ImageProcessingService
  ){}

  ngOnInit(): void {
    this.categoryService.getAllCategory()
    .pipe(
      map((x: CategoryItem[], i) => x.map((product: CategoryItem) => this.imageProcessingService.createCategoryImage(product)))
    )
    .subscribe({
      next: (data: any) => {
        this.categoryItemList = data;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

}
