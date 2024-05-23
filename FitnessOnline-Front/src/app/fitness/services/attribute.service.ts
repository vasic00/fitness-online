import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../config/environment';

@Injectable({
  providedIn: 'root'
})
export class AttributeService {

  constructor(private client: HttpClient) { }

  getSAByCategory(id: number): any {
    return this.client.get(`${environment.baseURL}${environment.attributesPath}` + '/category/' + id);
  }
}
