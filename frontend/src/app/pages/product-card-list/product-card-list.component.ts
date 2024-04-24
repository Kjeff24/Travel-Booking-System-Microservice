import { Component, inject } from '@angular/core';
import { ProductItem } from '../models/product-item';
import { ProductItemService } from '../../services/product-item-service/product-item-service.service';
import { CommonModule } from '@angular/common';
import { ProductSingleCardComponent } from '../product-single-card/product-single-card.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-card-list',
  standalone: true,
  imports: [CommonModule, ProductSingleCardComponent, RouterLink],
  templateUrl: './product-card-list.component.html',
  styleUrl: './product-card-list.component.css'
})
export class ProductCardListComponent {
  productItemList: ProductItem[] = [];
  filteredProductItemList: ProductItem[] = []

  productService: ProductItemService = inject(ProductItemService);

  constructor() {
    this.productService.getAllProducts().then((productItemList: ProductItem[]) => {
      this.productItemList = productItemList;
      this.filteredProductItemList = productItemList;
    });
  }
}
