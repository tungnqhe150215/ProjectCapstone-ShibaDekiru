import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-class-detail',
  templateUrl: './class-detail.component.html',
  styleUrls: ['./class-detail.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ClassDetailComponent {

}
