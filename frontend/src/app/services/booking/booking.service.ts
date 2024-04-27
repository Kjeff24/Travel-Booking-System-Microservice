import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BookingItem } from '../../models/booking-item';
import { CategoryItem } from '../../models/category-item';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  bookingServiceUrl = environment.booking_service_url

  constructor(private httpClient: HttpClient) { }

  public getAllCategory(): Observable<any>{
    return this.httpClient.get<any>(this.bookingServiceUrl + '/categories/all')
  }

  public getAllBooking(): Observable<any>{
    return this.httpClient.get<any>(this.bookingServiceUrl + '/bookings/all')
  }
}
