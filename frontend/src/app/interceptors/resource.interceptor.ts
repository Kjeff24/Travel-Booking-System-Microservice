import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { TokenService } from '../services/token/token.service';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class ResourceInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let intReq = request;
    const token = this.tokenService.getAccessToken();
  if(token !=null && request.url.includes('booking-service')){
    intReq = request.clone({headers: request.headers.set('Authorization', 'Bearer ' + token)});
  }

    return next.handle(intReq);
  }
}
