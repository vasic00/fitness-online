import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationInterceptorService implements HttpInterceptor {

  private token: string = "";

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    console.log('intercepting!!!!!!!!!!!');
    let isUndefined = typeof sessionStorage === 'undefined';
    if (!isUndefined && sessionStorage.getItem('user'))
      this.token = JSON.parse(sessionStorage.getItem('user') || '')!.token;
    else this.token = 'no token';
    if (this.token) {
      const modReq = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + this.token,
        },
      });
      return next.handle(modReq);
    }
    return next.handle(req);
  }
}
