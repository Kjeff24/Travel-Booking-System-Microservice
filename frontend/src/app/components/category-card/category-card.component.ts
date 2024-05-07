import { Component, Input } from '@angular/core';
import { CategoryItem } from '../../models/category-item';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.css'
})
export class CategoryCardComponent {
  isDashboardPage: boolean = false;

  constructor(
    private location: Location
  ){}

  ngOnIt(): void{
    const currentUrl = this.location.path();
    if (currentUrl.includes('dashboard')) {
      this.isDashboardPage = true;
    }
  }

  @Input() categoryItem!: CategoryItem;

  getRouterLink(): any[] {
    switch (this.categoryItem.code.toString()) {
      case 'ACC':
        return ['/store/category/accommodation', this.categoryItem.name, this.categoryItem.id];
      case 'HOT':
        return ['/store/category/hotel', this.categoryItem.name, this.categoryItem.id];
      case 'CAR':
        return ['/store/category/car-rental', this.categoryItem.name, this.categoryItem.id];
      case 'FLI':
        return ['/store/category/flight', this.categoryItem.name, this.categoryItem.id];
      default:
        return ['']; 
    }
  }

}
