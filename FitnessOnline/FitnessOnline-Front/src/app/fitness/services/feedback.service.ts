import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  constructor(private snackBar: MatSnackBar) { }

  showError(msg: string): void {
    this.snackBar.open(msg, 'Close', {
      duration: 4000,
      verticalPosition: 'top',
      panelClass: ['error']
    });
  }

  showMessage(msg: string): void {
    this.snackBar.open(msg, 'Close', {
      duration: 4000,
      verticalPosition: 'top',
      panelClass: ['message']
    });
  }
}
