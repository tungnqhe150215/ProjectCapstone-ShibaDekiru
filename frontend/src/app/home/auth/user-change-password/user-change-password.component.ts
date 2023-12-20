import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NotificationService} from 'src/app/core/services/notification.service';
import {UseServiceService} from '../use-service.service';
import {StorageService} from '../user-login/storage.service';
import {ChangePassword} from 'src/app/core/models/change-password';
import {Subscription} from 'rxjs';
import {EventBusService} from '../event-bus.service';

@Component({
  selector: 'app-user-change-password',
  templateUrl: './user-change-password.component.html',
  styleUrls: ['./user-change-password.component.css']
})
export class UserChangePasswordComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
    private eventBusService: EventBusService,
  ) {
  }

  error: any = {
    message: 'no'
  }
  success: any = {
    message: 'yes'
  }
  form: any = {};
  hide = true;
  updatePassword!: ChangePassword;
  status = 'Form Change password';
  newhide: any;
  newhide1: any;
  eventBusSub?: Subscription;

  changePassword() {
    // @ts-ignore
    this.updatePassword = new ChangePassword(this.form.password, this.form.newpassword, this.form.confirmpassword);

    this.userService.changePassword(this.updatePassword).subscribe(data => {
        console.log('dataPassword----->', data);

        this.notifiService.openSnackBar('Đổi mật khẩu thành công, bạn cần đăng nhập lại');
        this.logout();

      },
      error1 => {
        if (error1.status === 404) {
          this.notifiService.openSnackBar('Mật khẩu hiện tại của bạn không đúng. Vui lòng kiểm tra lại!');
        }

        if (error1.status === 400) {
          this.notifiService.openSnackBar('Mật khẩu mới không giống nhau. Vui lòng kiểm tra lại!');
        }
      }
    )

  }

  logout(): void {
    this.storageService.clean();
    this.userService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();
        this.router.navigate(['/login']);
        // window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
