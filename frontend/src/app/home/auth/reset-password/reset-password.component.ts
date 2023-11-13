import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UseServiceService } from '../use-service.service';
import { StorageService } from '../user-login/storage.service';
import { NewPassword } from 'src/app/core/models/new-password';



@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit{


  newPassword!:NewPassword;
  isLoggedIn = false;
  form: any = {};
  resetCode!: any;
  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
    private route: ActivatedRoute,
  ){}

  
  ngOnInit(): void {
    // this.resetCode = this.route.snapshot.queryParams.resetCode;
    this.route.paramMap.subscribe((parameters: ParamMap) => {
      this.resetCode = parameters.get('resetCode');
      console.log(this.resetCode);
      });
  }

  onSubmit(){
    this.newPassword = new NewPassword(this.form.newPassword, this.form.confirmNewPassword);
    this.userService.resetPassword(this.resetCode, this.newPassword)
    .subscribe(
      data =>{
        console.log(data);
        this.notifiService.openSnackBar('Cập nhật mật khẩu thành công! Vui lòng đăng nhập lại');
        this.router.navigate(['/login']);
      }
    )
  }

}
