import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  gateway_url = environment.gateway_url

  constructor(private httpClient: HttpClient) { }

  public getAllAccommodations(): Observable<any>{
    return this.httpClient.get<any>(this.gateway_url + '/api/booking-service/accommodation')
  }

  public getAllHotel(): Observable<any>{
    return this.httpClient.get<any>(this.gateway_url + '/api/booking-service/hotel')
  }

  public getAllFlight(): Observable<any>{
    return this.httpClient.get<any>(this.gateway_url + '/api/booking-service/flight')
  }

  public getAllCarRental(): Observable<any>{
    return this.httpClient.get<any>(this.gateway_url + '/api/booking-service/car-rental')
  }

  public searchAccommodations(query: string): Observable<any>{
    const url = `${this.gateway_url}/api/booking-service/accommodation/?${query}`;
    return this.httpClient.get<any>(url);  
  }
}
