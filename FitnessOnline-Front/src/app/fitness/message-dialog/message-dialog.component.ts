import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-message-dialog',
  templateUrl: './message-dialog.component.html',
  styleUrl: './message-dialog.component.css'
})
export class MessageDialogComponent {


  message: string = "";

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public matDialogRef: MatDialogRef<MessageDialogComponent>) { }

  submit() {
    this.matDialogRef.close(this.message);
  }
}
