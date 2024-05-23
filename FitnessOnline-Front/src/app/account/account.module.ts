import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { AntdModule } from '../antd/antd.module';
import { MaterialModule } from '../material/material.module';
import { ActivationRedirectComponent } from './activation-redirect/activation-redirect.component';


@NgModule({
  declarations: [
    RegistrationComponent,
    LoginComponent,
    ActivationRedirectComponent
  ],
  imports: [
    CommonModule,
    AccountRoutingModule,
    AntdModule,
    MaterialModule
  ]
})
export class AccountModule { }
