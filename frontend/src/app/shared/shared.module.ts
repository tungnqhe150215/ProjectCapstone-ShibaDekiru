import { NgModule } from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import { BackButtonDirective } from './directives/back-button.directive';
import { FormatLocalDateTimePipe } from './pipes/format-local-date-time.pipe';



@NgModule({
  declarations: [
    BackButtonDirective,
    FormatLocalDateTimePipe
  ],
    exports: [
        BackButtonDirective,
        FormatLocalDateTimePipe
    ],
  imports: [
    CommonModule
  ],
  providers: [
    DatePipe, // Thêm DatePipe vào providers
    // Các service khác
  ],
})
export class SharedModule { }
