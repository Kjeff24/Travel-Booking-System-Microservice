import { Component } from '@angular/core';
import { CategoryItem } from '../../models/category-item';
import { CategoryService } from '../../services/category/category.service';
import { CommonModule } from '@angular/common';
import { CategoryCardComponent } from '../category-card/category-card.component';

@Component({
  selector: 'app-store-main-page',
  standalone: true,
  imports: [CommonModule, CategoryCardComponent],
  templateUrl: './store-main-page.component.html',
  styleUrl: './store-main-page.component.css'
})
export class StoreMainPageComponent {
  categoryItemList!: CategoryItem[];

  constructor(
    private categoryService: CategoryService,
  ) {}

  ngOnInit(): void {
    this.getAllCategory();
  }

  getAllCategory(): void {this.categoryService.getAllCategory().subscribe({
    next: (data: any) => {
      this.categoryItemList = data;
    },
    error: () => {
      console.log( "Error");
    }
  })}
}
