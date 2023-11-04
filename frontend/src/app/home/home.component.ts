import { Component, ViewChild } from '@angular/core';
import { UseServiceService } from './auth/use-service.service';
import { StorageService } from './auth/user-login/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css', './home-style.css']
})
export class HomeComponent {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  email?: string;

  constructor(
    private userService: UseServiceService,
    private storageService: StorageService,
  ) { }


  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      // this.showAdminBoard = this.roles.includes('ADMIN');
      // this.showModeratorBoard = this.roles.includes('STUDENT');

      this.email = user.email;
    }
  }
}
