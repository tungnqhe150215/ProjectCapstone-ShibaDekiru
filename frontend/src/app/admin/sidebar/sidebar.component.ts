import { Component, OnInit, ViewChild, computed, signal } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EventBusService } from 'src/app/home/auth/event-bus.service';
import { UseServiceService } from 'src/app/home/auth/use-service.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit{

  eventBusSub?: Subscription;
  constructor(
    private userService: UseServiceService,
    private storageService: StorageService,
    private eventBusService: EventBusService,
    private router:Router,
  ){}
  ngOnInit(): void {
    this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logout();
      
    });
  }

  logout(): void {
    this.storageService.clean();
    this.userService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();
        this.router.navigate(['/home']);
        // window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });

   
  }
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
