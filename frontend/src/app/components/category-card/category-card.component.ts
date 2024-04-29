import { Component, Input } from '@angular/core';
import { CategoryItem } from '../../models/category-item';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.css'
})
export class CategoryCardComponent {

  @Input() categoryItem!: CategoryItem;

  getRouterLink(): any[] {
    switch (this.categoryItem.code) {
      case 'ACC':
        return ['/store/category/accommodation', this.categoryItem.id];
      case 'HOT':
        return ['/store/category/hotel', this.categoryItem.id];
      case 'CAR':
        return ['/store/category/car-rental', this.categoryItem.id];
      case 'FLI':
        return ['/store/category/flight', this.categoryItem.id];
      default:
        return ['']; 
    }
  }

}
