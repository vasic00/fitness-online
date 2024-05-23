import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../model/LoginRequest';
import { environment } from '../config/environment';
import { User } from '../model/User';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  login(loginRequest: LoginRequest): any {
    return this.httpClient.post(`${environment.baseURL}${environment.usersPath}/login`, loginRequest);
  }

  register(user: User): any {
    return this.httpClient.post(`${environment.baseURL}${environment.usersPath}/register`, user);
  }

  update(user: User): any {
    return this.httpClient.put(`${environment.baseURL}${environment.usersPath}`, user);
  }

  checkActivated(id: number): any {
    return this.httpClient.get(
      `${environment.baseURL}${environment.usersPath}/activated/` + id
    );
  }
  checkBlocked(id: number): any {
    return this.httpClient.get(
      `${environment.baseURL}${environment.usersPath}/blocked/` + id
    );
  }

}
