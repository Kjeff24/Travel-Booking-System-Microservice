import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { TokenService } from '../token/token.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient, private tokenService: TokenService) { }

  public getCartsTotalQuantity(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/get-order-quantity/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public getCartItems(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/get-cart-items/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public getCartItemsTotalPrice(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/get-total-price/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public addToCart(data: any): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<any>(
      this.gateway_url + '/api/order-service/add-to-cart',
      data,
      { headers, observe: 'response' }
    );
  }

  public addAllCartItems(data: any, userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<any>(
      this.gateway_url + `/api/order-service/add-all-to-cart/${userId}`,
      data,
      { headers, observe: 'response' }
    );
  }

  public deleteAllCartItems(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.delete<any>(
      this.gateway_url + `/api/order-service/delete-all-cart-items/${userId}`,
      { headers, observe: 'response' }
    );
  }

  public removeCartItem(userId: string, bookingId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.delete<any>(
      this.gateway_url + `/api/order-service/delete-from-cart?bookingId=${bookingId}&userId=${userId}`,
      { headers }
    );
  }

  public decreaseCartItem(data: any): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.patch<any>(
      this.gateway_url + '/api/order-service/decrease-cart-item',
      data,
      { headers }
    );
  }
}
