import { Component, ViewChild, computed, signal } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  // showFiller = false;
  // public isOpenUiElements = false;

  // public openUiElements() {
  //   this.isOpenUiElements = !this.isOpenUiElements;
  // }

  // @ViewChild('sidenav') sidenav!: MatSidenavModule;
  // isExpanded = true;
  // showSubmenu: boolean = false;
  // isShowing = false;
  // showSubSubMenu: boolean = false;

  // mouseenter() {
  //   if (!this.isExpanded) {
  //     this.isShowing = true;
  //   }
  // }

  // mouseleave() {
  //   if (!this.isExpanded) {
  //     this.isShowing = false;
  //   }
  // }
 
  // activeItem: number = -1; // Mặc định không có thẻ nào được kích hoạt.

  // setActiveItem(index: number) {
  //   this.activeItem = index;
  // }

  collapsed = signal(false);
  sidenavWidth = computed( () => this.collapsed() ? '65px' : '250px' );
}
