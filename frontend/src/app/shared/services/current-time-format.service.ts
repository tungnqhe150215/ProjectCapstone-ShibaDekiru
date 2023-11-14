import { Injectable } from '@angular/core';
import {DatePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class CurrentTimeFormatService {

  constructor(private datePipe: DatePipe) {}

  formatDate(date: Date): Date {
    const formattedDate = this.datePipe.transform(date, 'yyyy-MM-dd HH:mm:ss') as string;
    return new Date(formattedDate);
  }
}
