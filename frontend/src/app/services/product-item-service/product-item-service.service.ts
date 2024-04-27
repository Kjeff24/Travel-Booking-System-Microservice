import { Injectable } from '@angular/core';
import { ProductItem } from '../../models/product-item';

@Injectable({
  providedIn: 'root'
})
export class ProductItemService {

  url = 'http://localhost:3000/products';
  
  constructor() { }

  async getAllProducts(): Promise<ProductItem[]> {
    const data = await fetch(this.url);
    return await data.json() ?? [];
  }

  async getProductItemById(id: number): Promise<ProductItem | undefined> {
    const data = await fetch(`${this.url}/${id}`);
    return await data.json() ?? {};
  }

}
