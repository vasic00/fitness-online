import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-buying-dialog',
  templateUrl: './buying-dialog.component.html',
  styleUrl: './buying-dialog.component.css'
})
export class BuyingDialogComponent {

  numberFormControl = new FormControl('', [Validators.required]);
  dataRequired: boolean = false;
  requiredData: string = '';
  number: string = '';
  paying: string = 'live';
  constructor(public matDialogRef: MatDialogRef<BuyingDialogComponent>) { }
  render() {

    if (this.paying === 'card') {
      this.dataRequired = true;
      this.requiredData = 'Card number';
    } else if (this.paying === 'paypal') {
      this.dataRequired = true;
      this.requiredData = 'ID';
    } else {
      this.dataRequired = false;
      this.number = '';
    }
  }
  submit() {
    this.numberFormControl.markAsTouched();
    if (this.numberFormControl.valid || this.dataRequired === false)
      this.matDialogRef.close(this.paying + ' ' + this.number);
  }

}
