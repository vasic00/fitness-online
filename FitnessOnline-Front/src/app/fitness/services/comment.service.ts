import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../config/environment';
import { Comment } from '../../model/Comment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  postComment(comment: Comment): Observable<any> {
    return this.httpClient.post(`${environment.baseURL}${environment.commentsPath}`, comment);
  }
}
