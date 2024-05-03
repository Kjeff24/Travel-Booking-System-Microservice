import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems: any[] = [];
  private totalPrice: number = 0;
  private totalQuantity: number = 0;

  constructor() { 
    this.loadCartData();
  }

  addToCart(bookingId: string, price: number): void {
    // Check if the item already exists in the cart based on bookingId
    const existingItem = this.cartItems.find(item => item.bookingId === bookingId);

    if (existingItem) {
      // If the item already exists, update its quantity and totalPrice
      existingItem.quantity++;
      existingItem.totalPrice = existingItem.quantity * price;
    } else {
      // If the item does not exist, add it to the cart with quantity 1 and totalPrice equal to price
      this.cartItems.push({ bookingId, quantity: 1, totalPrice: price });
    }

    // Update the total price and quantity of all items in the cart
    this.updateCartData();
  }

  getCartItems(): any[] {
    return this.cartItems;
  }

  getTotalPrice(): number {
    return this.totalPrice;
  }

  getTotalQuantity(): number {
    return this.totalQuantity;
  }

  private updateCartData(): void {
    // Recalculate total price and total quantity
    this.totalPrice = this.cartItems.reduce((total, item) => total + item.totalPrice, 0);
    this.totalQuantity = this.cartItems.reduce((total, item) => total + item.quantity, 0);

    // Save cart data to session storage
    sessionStorage.setItem('cartItems', JSON.stringify(this.cartItems));
    sessionStorage.setItem('totalPrice', JSON.stringify(this.totalPrice));
    sessionStorage.setItem('totalQuantity', JSON.stringify(this.totalQuantity));
  }

  private loadCartData(): void {
    // Retrieve cart data from session storage
    const storedCartItems = sessionStorage.getItem('cartItems');
    const storedTotalPrice = sessionStorage.getItem('totalPrice');
    const storedTotalQuantity = sessionStorage.getItem('totalQuantity');

    if (storedCartItems && storedTotalPrice && storedTotalQuantity) {
      // Parse stored cart items and update service properties
      this.cartItems = JSON.parse(storedCartItems);
      this.totalPrice = JSON.parse(storedTotalPrice);
      this.totalQuantity = JSON.parse(storedTotalQuantity);
    }
  }
}
