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
      next: (data: CategoryItem[]) => {
        console.log('Data received');
        this.categoryItemList = data;
      },
    })
  }

  getRouterLink(code: string, id: string): any[] {
    switch (code) {
      case 'ACC':
        return ['/store/category/accommodation', id];
      case 'HOT':
        return ['/store/category/hotel', id];
      case 'CAR':
        return ['/store/category/car-rental', id];
      case 'FLI':
        return ['/store/category/flight', id];
      default:
        return ['']; 
    }
  }


  
}
