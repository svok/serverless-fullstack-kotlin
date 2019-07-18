import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CognitoUtil} from './service/cognito.service';
import {DynamoDBService} from './service/ddb.service';
import {UserLoginService} from './service/user-login.service';
import {UseractivityComponent} from './secure/useractivity/useractivity.component';
import {AwsUtil} from './service/aws.service';
import {UserRegistrationService} from './service/user-registration.service';
import {UserParametersService} from './service/user-parameters.service';
import {MyProfileComponent} from './secure/profile/myprofile.component';
import {JwtComponent} from './secure/jwttokens/jwt.component';
import {SecureHomeComponent} from './secure/landing/securehome.component';
import {AboutComponent, HomeComponent, HomeLandingComponent} from './public/home.component';
import {MFAComponent} from './public/auth/mfa/mfa.component';
import {RegisterComponent} from './public/auth/register/registration.component';
import {ForgotPassword2Component, ForgotPasswordStep1Component} from './public/auth/forgot/forgotPassword.component';
import {LogoutComponent, RegistrationConfirmationComponent} from './public/auth/confirm/confirmRegistration.component';
import {LoginComponent} from './public/auth/login/login.component';
import {NewPasswordComponent} from './public/auth/newpassword/newpassword.component';
import {ResendCodeComponent} from './public/auth/resend/resendCode.component';

@NgModule({
  declarations: [
    AppComponent,
    NewPasswordComponent,
    LoginComponent,
    LogoutComponent,
    RegistrationConfirmationComponent,
    ResendCodeComponent,
    ForgotPasswordStep1Component,
    ForgotPassword2Component,
    RegisterComponent,
    MFAComponent,
    AboutComponent,
    HomeLandingComponent,
    HomeComponent,
    UseractivityComponent,
    MyProfileComponent,
    SecureHomeComponent,
    JwtComponent,
    AppComponent  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    CognitoUtil,
    AwsUtil,
    DynamoDBService,
    UserRegistrationService,
    UserLoginService,
    UserParametersService
  ],
    bootstrap: [AppComponent]
})
export class AppModule { }
