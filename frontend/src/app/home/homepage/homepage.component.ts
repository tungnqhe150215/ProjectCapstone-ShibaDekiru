import { Component } from '@angular/core';
import { UseServiceService } from '../auth/use-service.service';
import { StorageService } from '../auth/user-login/storage.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css','../home-style.css']
})
export class HomepageComponent {

  // isLoggedIn = false;
  // // showAdminBoard = false;
  // // showModeratorBoard = false;
  // username?: string;
  // constructor(
  //   private userService: UseServiceService,
  //   private storageService: StorageService,
  // ){
    
  // }
  // ngOnInit(): void {
  //   this.isLoggedIn = this.storageService.isLoggedIn();

  //   if(this.isLoggedIn){
  //     const user = this.storageService.getUser();
  //   this.username = user.username;
  //   }
  //   this.logout();
  // }

  // logout(): void{
  //   this.userService.logout().subscribe({
  //     next: res =>{
  //       console.log(res);
  //       this.storageService.clean();
  //       window.location.reload();
  //     },
  //     error: err=>{
  //       console.log(err);
  //     }
  //   });
  // }
}
