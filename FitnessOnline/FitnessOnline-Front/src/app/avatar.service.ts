import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './config/environment';
import { Avatar } from './model/Avatar';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AvatarService {

  constructor(private client: HttpClient) { }

  getAllAvatars(): any {
    return this.client.get(`${environment.baseURL}${environment.avatarsPath}`);
  }
  mapAvatars(data: string[]): Array<Avatar> {
    let result: Array<Avatar> = [];
    data.forEach((element) => {
      result.push(new Avatar(element));
    });
    console.log(result);
    return result;
  }
}
