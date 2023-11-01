import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';

import { UserChangePasswordComponent } from './auth/user-change-password/user-change-password.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { HiraganaComponent } from './alphabet/hiragana/hiragana.component';
import { KatakanaComponent } from './alphabet/katakana/katakana.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'
import { FormsModule }   from '@angular/forms';
import { UserLoginComponent } from './auth/user-login/user-login.component';
import { UserRegisterComponent } from './auth/user-register/user-register.component';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    HomepageComponent,
    UserLoginComponent,
    UserChangePasswordComponent,
    ForgotPasswordComponent,
    HiraganaComponent,
    KatakanaComponent,
    UserRegisterComponent,
    
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
   
    
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    HttpClientModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class HomeModule { }
