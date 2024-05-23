import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from '../../model/Message';
import { environment } from '../../config/environment';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private httpClient: HttpClient) {

  }

  sendMessage(message: Message): any {
    return this.httpClient.post(`${environment.baseURL}${environment.messagesPath}`, message);
  }

  getReceivedByUser(unread: String, key: String): any {
    return this.httpClient.get(`${environment.baseURL}${environment.messagesPath}` + '/received/' + unread + '/' + key);
  }

  readMessage(id: number): any {
    return this.httpClient.put(`${environment.baseURL}${environment.messagesPath}` + '/received/' + id, null);
  }

  deleteMessage(id: number): any {
    return this.httpClient.delete(`${environment.baseURL}${environment.messagesPath}` + '/received/' + id);
  }
}
