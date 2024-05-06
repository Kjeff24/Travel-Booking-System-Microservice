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
    switch (this.categoryItem.code.toString()) {
      case 'ACC':
        return ['/store/category/accommodation'];
      case 'HOT':
        return ['/store/category/hotel'];
      case 'CAR':
        return ['/store/category/car-rental'];
      case 'FLI':
        return ['/store/category/flight'];
      default:
        return ['']; 
    }
  }

}
