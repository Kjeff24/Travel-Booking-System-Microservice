import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CategoryCardComponent } from '../../../components/category-card/category-card.component';
import { CategoryService } from '../../../services/category/category.service';
import { CategoryItem } from '../../../models/category-item';
import { RouterLink } from '@angular/router';

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
    private categoryService: CategoryService
  ){}

  ngOnInit(): void {
    this.categoryService.getAllCategory().subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

}
