import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  
  bookingServiceUrl = environment.booking_service_url

  constructor(private httpClient: HttpClient) { }

  public getAllCategory(): Observable<any>{
    return this.httpClient.get<any>(this.bookingServiceUrl + '/categories/all')
  }

  public getAllBooking(): Observable<any>{
    return this.httpClient.get<any>(this.bookingServiceUrl + '/bookings/all')
  }
}
