import { Component, OnInit  } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UseServiceService } from '../use-service.service';
import { StorageService } from '../user-login/storage.service';
import { ForgotPassword } from 'src/app/core/models/forgot-password';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit{


  forgotPassword!: ForgotPassword;
  isLoggedIn = false;
  formlogin: any = {
    email: null,
  };
  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
  ){}
  ngOnInit(): void {


  }
  onSubmitEmail(){
    this.forgotPassword = new ForgotPassword(this.formlogin.email);
    this.userService.forgotPassword(this.forgotPassword)
    .subscribe(
      data =>{
        console.log(data);
        this.router.navigate(['/check-mail']);
        this.notifiService.openSnackBar('Xin vui lòng kiểm tra Email');
      }
      , error => {
        if (error.status === 403) {
          this.notifiService.openSnackBar('Email không tìm thấy. Xin vui lòng kiểm tra lại email.');
        }
      }
    )
  }

  // handleResponse(data){
  //   this.toa
  // }

}
