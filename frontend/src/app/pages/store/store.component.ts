import { Component, OnInit, inject } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CategoryService } from '../../services/category/category.service';
import { CategoryItem } from '../../models/category-item';

@Component({
  selector: 'app-store',
  standalone: true,
  imports: [CommonModule, RouterLink, NavbarComponent, FooterComponent, RouterOutlet, CommonModule],
  templateUrl: './store.component.html',
  styleUrl: './store.component.css'
})
export class StoreComponent implements OnInit{

  categoryItemList!: CategoryItem[];

  constructor(
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    
    this.categoryService.getAllCategory().subscribe({
      next: (data: any) => {
        this.categoryItemList = data.body;
      },
      error: () => {
        console.log( "Error");
      }
    })


  }

  

  getRouterLink(code: string, categoryName: string, categoryId: string): any[] {
    switch (code) {
      case 'ACC':
        return ['/store/category/accommodation', categoryName, categoryId];
      case 'HOT':
        return ['/store/category/hotel', categoryName, categoryId];
      case 'CAR':
        return ['/store/category/car-rental', categoryName, categoryId];
      case 'FLI':
        return ['/store/category/flight', categoryName, categoryId];
      default:
        return ['']; 
    }
  }

  


  
}
