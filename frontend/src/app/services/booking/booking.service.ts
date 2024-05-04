import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  gateway_url = environment.gateway_url;

  constructor(
    private httpClient: HttpClient,
    private tokenService: TokenService
  ) {}

  public getAllAccommodations(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/accommodation'
    );
  }

  public getAllHotel(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/hotel'
    );
  }

  public getAllFlight(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/flight'
    );
  }

  public getAllCarRental(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/car-rental'
    );
  }

  public getNumberofProducts(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/get-product-size'
    );
  }

  public getCartsTotalQuantity(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/order-items/get-order-quantity/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public getCartItems(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/order-items/get-cart-items/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public getCartItemsTotalPrice(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/order-items/get-total-price/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public addToCart(data: any): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<any>(
      this.gateway_url + '/api/order-service/order-items/add-to-cart',
      data,
      { headers, observe: 'response' }
    );
  }

  public addAllCartItems(data: any, userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<any>(
      this.gateway_url + `/api/order-service/order-items/add-all-to-cart/${userId}`,
      data,
      { headers, observe: 'response' }
    );
  }

  public removeCartItem(userId: string, bookingId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.delete<any>(
      this.gateway_url + `/api/order-service/order-items/delete-from-cart?bookingId=${bookingId}&userId=${userId}`,
      { headers }
    );
  }

  public decreaseCartItem(data: any): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.patch<any>(
      this.gateway_url + '/api/order-service/order-items/decrease-cart-item',
      data,
      { headers }
    );
  }

  public getProductByBookingOfferId(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/find-by-id/${bookingId}`,
      {observe: 'response' }
    );
  }

  public getCategoryById(categoryId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/category-service/${categoryId}`,
      {observe: 'response' }
    );
  }

}
