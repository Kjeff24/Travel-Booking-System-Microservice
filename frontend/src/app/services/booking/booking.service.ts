import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
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
  public getNumberofProducts(): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/number-of-products',
      {headers, observe: 'response'}
    );
  }

  public getProductByBookingOfferId(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/find-by-id/${bookingId}`,
      {observe: 'response' }
    );
  }

  

}
