import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AdminModule} from "./admin/admin.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
// import { LecturersRoutingModule } from './lecturers/lecturers-routing.module';
import { LecturersModule } from './lecturers/lecturers.module';
import { HomeComponent } from './home/home.component';
import {HomeModule} from "./home/home.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
    imports: [
        BrowserModule,
        AdminModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatButtonModule,
        MatIconModule,
        LecturersModule,
        HomeModule,
        FontAwesomeModule
        // LecturersRoutingModule,

    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
