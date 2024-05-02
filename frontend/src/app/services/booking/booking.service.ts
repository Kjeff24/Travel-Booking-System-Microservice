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

  public getCartsTotalQuantity(userId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/order-service/order-items/get-order-quantity/${userId}`,
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

}
