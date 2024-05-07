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
    const existingItem = this.cartItems.find(item => item.bookingId === bookingId);

    if (existingItem) {
      existingItem.quantity++;
      existingItem.totalPrice = existingItem.quantity * price;
    } else {
      this.cartItems.push({ bookingId, quantity: 1, totalPrice: price });
    }
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

  public decreaseCartItem(bookingId: string, price: number): void {
    const existingItemIndex = this.cartItems.findIndex(item => item.bookingId === bookingId);
  
    if (existingItemIndex !== -1) {
      const existingItem = this.cartItems[existingItemIndex];
      
      if (existingItem.quantity > 1) {
        existingItem.quantity--;
        existingItem.totalPrice -= price;
      } else {
        this.cartItems.splice(existingItemIndex, 1);
      }
  
      this.updateCartData();
    }
  }

  public deleteCartItem(bookingId: string): void {
    const existingItemIndex = this.cartItems.findIndex(item => item.bookingId === bookingId);
  
    if (existingItemIndex !== -1) {
      // Remove the item from the cartItems array
      this.cartItems.splice(existingItemIndex, 1);
  
      // Update the total price and quantity of all items in the cart
      this.updateCartData();
    }
  }
  

  private updateCartData(): void {
    this.totalPrice = this.cartItems.reduce((total, item) => total + item.totalPrice, 0);
    this.totalQuantity = this.cartItems.reduce((total, item) => total + item.quantity, 0);
  
    sessionStorage.setItem('cartItems', JSON.stringify(this.cartItems));
    sessionStorage.setItem('totalPrice', JSON.stringify(this.totalPrice));
    sessionStorage.setItem('totalQuantity', JSON.stringify(this.totalQuantity));
  }

  private loadCartData(): void {
    const storedCartItems = sessionStorage.getItem('cartItems');
    const storedTotalPrice = sessionStorage.getItem('totalPrice');
    const storedTotalQuantity = sessionStorage.getItem('totalQuantity');

    if (storedCartItems && storedTotalPrice && storedTotalQuantity) {
      this.cartItems = JSON.parse(storedCartItems);
      this.totalPrice = JSON.parse(storedTotalPrice);
      this.totalQuantity = JSON.parse(storedTotalQuantity);
    }
  }

  clearCart(): void {
    sessionStorage.removeItem('cartItems');
    sessionStorage.removeItem('totalQuantity');
    sessionStorage.removeItem('totalPrice');
  }
}
