import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { AccommodationItem } from '../../models/accommodation-item';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient) { }

  public getAllAccommodations(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/accommodation',
      {observe: 'response'}
    );
  }

  public getAllAccommodationsByCategoryId(categoryId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/accommodation/category/${categoryId}`,
      {observe: 'response'}
    );
  }

  public getAccommodationById(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/accommodation/${bookingId}`,
      {observe: 'response'}
    );
  }

  public createAccommodation(data: AccommodationItem): Observable<any> {
    return this.httpClient.post<any>(
      this.gateway_url + '/api/booking-service/accommodation',
      data,
      {observe: 'response'}
    );
  }

  public updateAccommodation(bookingId:string, data: AccommodationItem): Observable<any> {
    return this.httpClient.put<any>(
      this.gateway_url + `/api/booking-service/accommodation/update/${bookingId}`,
      data,
      {observe: 'response'}
    );
  }
}
