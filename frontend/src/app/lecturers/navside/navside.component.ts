import { Component, OnInit, computed, signal } from '@angular/core';
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
  constructor(
    private storageService: StorageService,
  ){}

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
  }


}
