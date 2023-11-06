import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatLocalDateTime'
})
export class FormatLocalDateTimePipe implements PipeTransform {

  transform(dateTimeString: string): string {
    return dateTimeString.replace('T', ' ');
  }

}
