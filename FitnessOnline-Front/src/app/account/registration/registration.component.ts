import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomValidators } from './custom-validators';
import { Avatar } from '../../model/Avatar';
import { AvatarService } from '../../avatar.service';
import { UserService } from '../user.service';
import { User } from '../../model/User';
import { Router } from '@angular/router';
import { FeedbackService } from '../../fitness/services/feedback.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {

  registrationForm!: FormGroup;
  public avatars: Array<Avatar> = [];
  public selectedAvatar?: Avatar;


  constructor(private formBuilder: FormBuilder,
    private avatarService: AvatarService,
    private userService: UserService,
    private feedbackService: FeedbackService,
    private router: Router) {
    this.avatarService.getAllAvatars().subscribe((data: Array<string>) => {
      console.log(data);
      this.avatars = this.avatarService.mapAvatars(data);
    });
  }

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
      email: ['', [Validators.required, Validators.email]],
      city: ['', Validators.required],
    },
      { validator: CustomValidators.MatchingPasswords }

    )
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      let user: User = new User();
      user.firstname = this.registrationForm.value.firstname;
      user.lastname = this.registrationForm.value.lastname;
      user.username = this.registrationForm.value.username;
      user.password = this.registrationForm.value.password;
      user.email = this.registrationForm.value.email;
      user.city = this.registrationForm.value.city;
      if (this.selectedAvatar != null && this.selectedAvatar != undefined) {
        user.avatar = this.selectedAvatar.path;
      }
      this.userService.register(user).subscribe({
        next: (value: any) => {
          this.feedbackService.showMessage("Successful registration! Activation link has been sent to your email.")
          this.router.navigate(['auth']);
          this.registrationForm.reset();
        },
        error: (error: any) => {
          this.feedbackService.showError("User with that username already exists!");
        }
      });
    }
    else {
      Object.values(this.registrationForm.controls).forEach((control) => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  onSelectionChange() {
    console.log(this.selectedAvatar!.path);
  }

  submitForm() {
    this.onSubmit();
  }

}


