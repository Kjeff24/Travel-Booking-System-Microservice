import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { FlightItem } from '../../models/flight-item';

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient) { }

  public getAllFlight(): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + '/api/booking-service/flight',
      {observe: 'response'}
    );
  }

  public getAllFlightsByCategoryId(categoryId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/flight/category/${categoryId}`,
      {observe: 'response'}
    );
  }

  public getFlightById(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/flight/${bookingId}`,
      {observe: 'response'}
    );
  }

  public getCategoryByFlightId(bookingId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/booking-service/flight/find-category-by-booking-id/${bookingId}`,
      {observe: 'response'}
    );
  }

  public createFlight(data: FlightItem): Observable<any> {
    return this.httpClient.post<any>(
      this.gateway_url + '/api/booking-service/flight',
      data,
      {observe: 'response'}
    );
  }

  public updateFlight(bookingId:string, data: FlightItem): Observable<any> {
    return this.httpClient.put<any>(
      this.gateway_url + `/api/booking-service/flight/update/${bookingId}`,
      data,
      {observe: 'response'}
    );
  }
}
