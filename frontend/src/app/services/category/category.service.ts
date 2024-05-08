import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryItem } from '../../models/category-item';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  gateway_url = environment.gateway_url

  constructor(private httpClient: HttpClient) { }

  public getAllCategory(): Observable<any>{
    return this.httpClient.get<any>(this.gateway_url + '/api/category-service/all')
  }

  public getCategoryById(categoryId: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/category-service/${categoryId}`,
      {observe: 'response' }
    );
  }

  public getAllCategoryByCode(code: string): Observable<any> {
    return this.httpClient.get<any>(
      this.gateway_url + `/api/category-service/by-code?code-name=${code}`,
      {observe: 'response' }
    );
  }

  public createCategory(data: CategoryItem): Observable<any> {
    return this.httpClient.post<any>(
      this.gateway_url + '/api/category-service/create',
      data,
      {observe: 'response'}
    );
  }

  public updateCategory(categoryId:string, data: CategoryItem): Observable<any> {
    return this.httpClient.put<any>(
      this.gateway_url + `/api/category-service/update/${categoryId}`,
      data,
      {observe: 'response'}
    );
  }
}
