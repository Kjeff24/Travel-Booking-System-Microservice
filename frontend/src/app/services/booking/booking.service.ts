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
    private httpClient: HttpClient
  ) {}
  public getNumberofProducts(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/number-of-products',
      { observe: 'response'}
    );
  }

  public getProductByBookingOfferId(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/find-by-id/${bookingId}`,
      {observe: 'response' }
    );
  }

  

}
