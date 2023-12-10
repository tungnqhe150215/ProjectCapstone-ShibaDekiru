import { NgModule } from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import { BackButtonDirective } from './directives/back-button.directive';
import { FormatLocalDateTimePipe } from './pipes/format-local-date-time.pipe';
import { FilePreviewComponent } from './component/file-preview/file-preview.component';



@NgModule({
  declarations: [
    BackButtonDirective,
    FormatLocalDateTimePipe,
    FilePreviewComponent
  ],
    exports: [
        BackButtonDirective,
        FormatLocalDateTimePipe,
        FilePreviewComponent
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
