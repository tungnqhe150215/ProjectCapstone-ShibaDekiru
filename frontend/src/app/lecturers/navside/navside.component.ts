import { Component, computed, signal } from '@angular/core';

@Component({
  selector: 'app-navside',
  templateUrl: './navside.component.html',
  styleUrls: ['./navside.component.css']
})
export class NavsideComponent {
  collapsed = signal(false);
  sidenavWidth = computed( () => this.collapsed() ? '65px' : '250px' );
}
