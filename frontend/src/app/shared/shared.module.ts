import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BackButtonDirective } from './directives/back-button.directive';
import { FormatLocalDateTimePipe } from './pipes/format-local-date-time.pipe';



@NgModule({
  declarations: [
    BackButtonDirective,
    FormatLocalDateTimePipe
  ],
  exports: [
    BackButtonDirective
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
