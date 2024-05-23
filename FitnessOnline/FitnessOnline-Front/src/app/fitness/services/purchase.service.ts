import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../config/environment';
import { Purchase } from '../../model/Purchase';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  constructor(private client: HttpClient) { }

  getByBuyer(): any {
    return this.client.get(`${environment.baseURL}${environment.purchasesPath}` + '/buyer');
  }

  participate(purchase: Purchase): any {
    return this.client.post(`${environment.baseURL}${environment.purchasesPath}`, purchase);
  }

}
