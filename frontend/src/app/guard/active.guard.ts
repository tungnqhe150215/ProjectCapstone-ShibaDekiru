import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { NotificationService } from '../core/services/notification.service';
import { UseServiceService } from '../home/auth/use-service.service';
import { StorageService } from '../home/auth/user-login/storage.service';

@Injectable({
  providedIn: 'root'
})
export class ActiveGuard implements CanActivate {
  constructor(
    private router: Router, 
    private notifi: NotificationService, 
    private userService: UseServiceService,
    private storageService: StorageService,) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (this.storageService.getUser().isCreatedByAdmin === true) {
        console.log(route);
        console.log(state);
        return true;
      } else {
        this.router.navigate(['permission-denied']);
        return false;
      }
  }
}
