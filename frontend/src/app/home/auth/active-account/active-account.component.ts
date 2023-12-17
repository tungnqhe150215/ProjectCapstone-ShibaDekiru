import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UseServiceService } from '../use-service.service';
import { StorageService } from '../user-login/storage.service';

@Component({
  selector: 'app-active-account',
  templateUrl: './active-account.component.html',
  styleUrls: ['./active-account.component.css']
})
export class ActiveAccountComponent implements OnInit{

  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
    private route:ActivatedRoute,
  ){}
  ngOnInit(): void {

  }

  activationMessage ='';
  countdown: number = 5; // 5second
  isButtonClicked: boolean = false;
  activateAccount1() {
    this.route.params.subscribe(params => {
      const resetCode = params['resetCode'];

      if (resetCode && !this.isButtonClicked) {
        this.userService.verifyEmail(resetCode).subscribe(
          response => {
            this.userService = response;
            this.activationMessage = 'Kích hoạt tài khoản thành công !!';
            console.log('Kích hoạt tài khoản thành Công');
            this.startCountdown();
          },
          Done => {
          }
        );
      }
    });
  }



  activateAccount() {
    this.route.params.subscribe(params => {
      const resetCode = params['resetCode'];

      if (resetCode  && this.isButtonClicked) {
        this.userService.verifyEmail(resetCode).subscribe(
          response => {
            this.activationMessage = response;
          },
          error => {
            this.activationMessage = 'Kích hoạt tài khoản thành công !!';
            this.startCountdown();
          }
        );
      } else {
        this.activationMessage = 'Mã kích hoạt đã hết hạn ';
      }
    });
  }

  startCountdown() {
    const intervalId = setInterval(() => {
      this.countdown--;

      if (this.countdown <= 0) {
        clearInterval(intervalId);
        this.redirectToLogin();
      }
    }, 1000);
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }
}
