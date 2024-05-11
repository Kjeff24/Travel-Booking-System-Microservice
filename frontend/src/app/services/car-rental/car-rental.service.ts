import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { CarRentalItem } from '../../models/car-rental-item';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root'
})
export class CarRentalService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient, private tokenService: TokenService) { }

  public getAllCarRental(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/car-rental',
      {observe: 'response'}
    );
  }

  public getAllCarRentalByCategoryId(categoryId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/car-rental/category/${categoryId}`
    );
  }

  public getCarRentalById(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/car-rental/${bookingId}`
    );
  }

  public createCarRental(data: FormData): Observable<any> {
    return this.httpClient.post<any>(
      this.gateway_url + '/api/booking-service/car-rental',
      data,
      {observe: 'response'}
    );
  }

  public updateCarRental(bookingId:string, data: FormData): Observable<any> {
    return this.httpClient.put<any>(
      this.gateway_url + `/api/booking-service/car-rental/update/${bookingId}`,
      data,
      {observe: 'response'}
    );
  }
}
