import { Component, OnInit, computed, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { EventBusService } from 'src/app/home/auth/event-bus.service';
import { UseServiceService } from 'src/app/home/auth/use-service.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';

@Component({
  selector: 'app-navside',
  templateUrl: './navside.component.html',
  styleUrls: ['./navside.component.css']
})
export class NavsideComponent implements OnInit{
  collapsed = signal(false);
  sidenavWidth = computed( () => this.collapsed() ? '65px' : '250px' );
  currentUser: any;
  eventBusSub?: Subscription;
  constructor(
    private storageService: StorageService,
    private eventBusService: EventBusService,
    private userService: UseServiceService,
    private router:Router,
  ){}

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();

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

}
