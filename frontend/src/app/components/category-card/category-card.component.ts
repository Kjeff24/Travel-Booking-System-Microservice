import { Component, Input } from '@angular/core';
import { BookingItem } from '../../models/booking-item';
import { CategoryItem } from '../../models/category-item';

@Component({
  selector: 'app-category-card',
  standalone: true,
  imports: [],
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.css'
})
export class CategoryCardComponent {

  @Input() categoryItem!: CategoryItem;
}
