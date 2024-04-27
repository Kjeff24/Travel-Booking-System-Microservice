import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  token_url = environment.token_url;

  constructor(private httpClient: HttpClient) { }

  public getToken(code: string, code_verifier: string): Observable<any> {
    let body = new HttpParams()
    .set('grant_type', environment.grant_type)
    .set('client_id', environment.client_id)
    .set('redirect_uri', environment.redirect_uri)
    .set('scope', environment.scope)
    .set('code_verifier', code_verifier)
    .set('code', code)
    const basic_auth = 'Basic '+ btoa('angular-client:secret');
    const headers_object = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': basic_auth
    });
    const httpOptions = { headers: headers_object};
    return this.httpClient.post<any>(this.token_url, body, httpOptions);
  }
  
}