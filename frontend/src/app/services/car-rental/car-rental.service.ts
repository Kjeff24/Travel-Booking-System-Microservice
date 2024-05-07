import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { CarRentalItem } from '../../models/car-rental-item';

@Injectable({
  providedIn: 'root'
})
export class CarRentalService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient) { }

  public getAllCarRental(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/car-rental',
      {observe: 'response'}
    );
  }

  public getAllCarRentalByCategoryId(categoryId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/car-rental/category/${categoryId}`,
      {observe: 'response'}
    );
  }

  public getCarRentalById(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/car-rental/${bookingId}`,
      {observe: 'response'}
    );
  }

  public getCategoryByCarRentalId(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/car-rental/find-category-by-booking-id/${bookingId}`,
      {observe: 'response'}
    );
  }

  public createCarRental(data: CarRentalItem): Observable<any> {
    return this.httpClient.post<any>(
      this.gateway_url + '/api/booking-service/car-rental',
      data,
      {observe: 'response'}
    );
  }

  public updateCarRental(bookingId:string, data: CarRentalItem): Observable<any> {
    return this.httpClient.put<any>(
      this.gateway_url + `/api/booking-service/car-rental/update/${bookingId}`,
      data,
      {observe: 'response'}
    );
  }
}
