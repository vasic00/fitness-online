import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../model/User';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-account-settings-dialog',
  templateUrl: './account-settings-dialog.component.html',
  styleUrl: './account-settings-dialog.component.css'
})
export class AccountSettingsDialogComponent implements OnInit {


  form!: FormGroup;
  user: User | null = null;

  constructor(private formBuilder: FormBuilder,
    public matDialogRef: MatDialogRef<AccountSettingsDialogComponent>) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      password: ['', Validators.minLength(8)],
      email: ['', [Validators.required, Validators.email]],
      city: ['', Validators.required],
    },

    );
    this.user = JSON.parse(sessionStorage.getItem("user")!);
    this.form.get("firstname")?.setValue(this.user?.firstname);
    this.form.get("lastname")?.setValue(this.user?.lastname);
    this.form.get("password")?.setValue('');
    this.form.get("email")?.setValue(this.user?.email);
    this.form.get("city")?.setValue(this.user?.city);
  }

  onSubmit() {
    if (this.form.valid) {
      this.user!.firstname = this.form.get("firstname")?.value;
      this.user!.lastname = this.form.get("lastname")?.value;
      let pass = this.form.get("password")?.value;
      if (pass !== null && pass !== undefined && pass.toString() !== '')
        this.user!.password = pass;
      this.user!.email = this.form.get("email")?.value;
      this.user!.city = this.form.get("city")?.value;
      this.matDialogRef.close(this.user);
    }
  }

  submitForm() {
    this.onSubmit();
  }
}
