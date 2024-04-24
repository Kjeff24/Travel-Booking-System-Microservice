import { Component, Input } from '@angular/core';
import { ProductItem } from '../models/product-item';

@Component({
  selector: 'app-product-single-card',
  standalone: true,
  imports: [],
  templateUrl: './product-single-card.component.html',
  styleUrl: './product-single-card.component.css'
})
export class ProductSingleCardComponent {
  @Input() productItem!: ProductItem;
}
