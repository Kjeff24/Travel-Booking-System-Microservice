import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { HotelItem } from '../../models/hotel-item';

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient) { }

  public getAllHotel(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/hotel',
      {observe: 'response'}
    );
  }

  public getHotelById(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/hotel/${bookingId}`,
      {observe: 'response'}
    );
  }

  public getCategoryByHotelId(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/hotel/find-category-by-booking-id/${bookingId}`,
      {observe: 'response'}
    );
  }

  public createHotel(data: HotelItem): Observable<any> {
    return this.httpClient.post<any>(
      this.gateway_url + '/api/booking-service/hotel',
      data,
      {observe: 'response'}
    );
  }

  public updateHotel(bookingId:string, data: HotelItem): Observable<any> {
    return this.httpClient.put<any>(
      this.gateway_url + `/api/booking-service/hotel/update/${bookingId}`,
      data,
      {observe: 'response'}
    );
  }
}
