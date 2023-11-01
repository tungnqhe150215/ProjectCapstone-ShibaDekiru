import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import { HomeRoutingModule } from './home-routing.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { UserLoginComponent } from './auth/user-login/user-login.component';
import { UserChangePasswordComponent } from './auth/user-change-password/user-change-password.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { HiraganaComponent } from './alphabet/hiragana/hiragana.component';
import { KatakanaComponent } from './alphabet/katakana/katakana.component';
import { HiraganaDetailComponent } from './alphabet/hiragana/hiragana-detail/hiragana-detail.component';
import { KatakanaDetailComponent } from './alphabet/katakana/katakana-detail/katakana-detail.component';
import { ClassListComponent } from './class/class-list/class-list.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";




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
    HiraganaDetailComponent,
    KatakanaDetailComponent,
    ClassListComponent,
  ],
  exports: [
    FooterComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    MatCardModule,
    FontAwesomeModule
  ]
})
export class HomeModule { }
