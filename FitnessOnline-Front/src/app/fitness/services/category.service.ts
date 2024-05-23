import { Injectable } from '@angular/core';
import { environment } from '../../config/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private client: HttpClient) { }

  getCategoryByName(name: string): any {
    return this.client.get(`${environment.baseURL}${environment.categoriesPath}` + '/' + name);
  }

  getCategories(): any {
    return this.client.get(`${environment.baseURL}${environment.categoriesPath}`);
  }

  getSubscribedCategories(): any {
    return this.client.get(`${environment.baseURL}${environment.subscriptionsPath}`);
  }

  deleteSubscription(id: number): any {
    return this.client.delete(`${environment.baseURL}${environment.subscriptionsPath}` + '/' + id);
  }

  addSubscription(id: number): any {
    return this.client.post(`${environment.baseURL}${environment.subscriptionsPath}` + "?categoryId=" + id, null);
  }
}
