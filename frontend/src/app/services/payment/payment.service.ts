import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from '../token/token.service';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  gateway_url = environment.gateway_url;

  constructor(private httpClient: HttpClient, private tokenService: TokenService) { }

  public getAllPayments(): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + `/api/payment-service`,
      { headers, observe: 'response' }
    );
  }

  public getNumberOfPayments(): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(
      this.gateway_url + '/api/payment-service/number-of-payments',
      { headers, observe: 'response' }
    );
  }

  public makePayment(data: any): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<any>(
      this.gateway_url + '/api/payment-service',
      data,
      { headers, observe: 'response' }
    );
  }

  public deletePaymentById(paymentId: string): Observable<any> {
    const token = this.tokenService.getAccessToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.delete<any>(
      this.gateway_url + `/api/payment-service/${paymentId}`,
      { headers, observe: 'response' }
    );
  }
}
