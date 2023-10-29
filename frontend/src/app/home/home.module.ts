import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { UserLoginComponent } from './auth/user-login/user-login.component';
import { UserChangePasswordComponent } from './auth/user-change-password/user-change-password.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { HiraganaComponent } from './hiragana/hiragana.component';
import { KatakanaComponent } from './katakana/katakana.component';



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
  ],
  exports: [
    FooterComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule
  ]
})
export class HomeModule { }
