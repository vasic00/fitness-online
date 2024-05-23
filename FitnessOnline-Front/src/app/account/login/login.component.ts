import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from '../../model/LoginRequest';
import { UserService } from '../user.service';
import { User } from '../../model/User';
import { catchError, throwError } from 'rxjs';
import { FeedbackService } from '../../fitness/services/feedback.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  invalidCredentials: string = "Invalid credentials.";

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private feedbackService: FeedbackService,
    private userService: UserService,) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    sessionStorage.setItem("guest", "true");
    sessionStorage.removeItem("browseData");
    sessionStorage.removeItem("profileBrowseData");
  }

  onSubmit() {
    if (this.loginForm.valid) {
      let loginRequest: LoginRequest = new LoginRequest();
      loginRequest.password = this.loginForm.value.password;
      loginRequest.username = this.loginForm.value.username;
      this.userService.login(loginRequest).pipe(catchError(error => {
        this.feedbackService.showError(this.invalidCredentials);
        return throwError(() => new Error(""));
      })).subscribe((result: User) => {
        this.userService.checkBlocked(result.id).subscribe((blocked: boolean) => {
          if (blocked) {
            this.feedbackService.showError(this.invalidCredentials);
          }
          else {
            this.userService.checkActivated(result.id).subscribe((activated: boolean) => {
              if (activated) {
                sessionStorage.clear();
                sessionStorage.setItem('guest', 'false');
                sessionStorage.setItem('user', JSON.stringify(result));
                this.feedbackService.showMessage("Successful login!");
                // sessionStorage.removeItem("browseData");
                // sessionStorage.removeItem("profileBrowseData");
                this.router.navigate(['/fitness']);
              }
              else {
                this.feedbackService.showError("You have to activate your account. Activation link has been sent to your email.");
              }
            })
          }
        })
      })
    }
    else {
      Object.values(this.loginForm.controls).forEach((control) => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  submitForm() {
    this.onSubmit();
  }

}
