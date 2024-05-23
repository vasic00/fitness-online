import { Component, Input, OnInit } from '@angular/core';
import { Comment } from '../../model/Comment';

@Component({
  selector: 'app-comment-box',
  templateUrl: './comment-box.component.html',
  styleUrl: './comment-box.component.css'
})
export class CommentBoxComponent implements OnInit {

  @Input('comment') comment: Comment = new Comment();

  constructor() {

  }

  ngOnInit() {
    console.log(this.comment.id);
    console.log(this.comment.content);
    console.log(this.comment.programId);
    console.log(this.comment.time);

  }

}
