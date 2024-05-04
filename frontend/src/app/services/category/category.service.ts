import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  gateway_url = environment.gateway_url

  constructor(private httpClient: HttpClient) { }

  public getAllCategory(): Observable<any>{
    return this.httpClient.get<any>(this.gateway_url + '/api/category-service/all', {observe: 'response'})
  }
}
