import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { NotificationService } from '../core/services/notification.service';
import { UseServiceService } from '../home/auth/use-service.service';
import { StorageService } from '../home/auth/user-login/storage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleClassGuard implements CanActivate {
  constructor(
    private router: Router, 
    private notifi: NotificationService, 
    private userService: UseServiceService,
    private storageService: StorageService,) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const userRole= this.storageService.getUser()?.role?.roleType;  
    if(userRole){
      if(userRole === 'ADMIN' && route.data['RoleType'].includes('ADMIN')){
        console.log("Role:", route.data['RoleType']);
        return true;
      }else if(userRole === 'LECTURE' && route.data['RoleType'].includes('LECTURE')){
        console.log("Role:", route.data['RoleType']);
        return true;
      }
    }
    console.log(route);
    console.log(state);
    this.router.navigate(['permission-denied']);
    console.error('Permission denied');
    return false;  
    // const RoleType = sessionStorage.getItem('roleType');
    // const RoleType = route.data['roleType'] as string

    // if(this.storageService.getUser().role.roleType === 'ADMIN'){
    //   console.log('Role:', this.storageService.getUser().role.roleType)
    //   return true;
    // } else if(this.storageService.getUser().role.roleType === 'LECTURE'){
    //   console.log('Role:', this.storageService.getUser().role.roleType)
    //   return true;
    // }
    // else{
    //   this.router.navigate(['unthorization']);
    //   // this.router.navigate(['login']);
    //   this.notifi.openSnackBar('Your permission not true !')
    //   return false;
    // }

    // return true;

    
  }


  // CanLecturerActive(route: ActivatedRouteSnapshot,state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree{

  // }
  
}
