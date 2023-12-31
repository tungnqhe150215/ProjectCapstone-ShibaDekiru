import { Component, OnInit, AfterViewInit } from '@angular/core';
import { UseServiceService } from '../use-service.service';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { RoleInte } from 'src/app/core/models/role';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit, AfterViewInit {


  hide = true;
  showPassword = false;
  checked = false;
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  //Login
  formlogin: any = {
    email: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roleId!: number;
  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
  ) { }


  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.roleId = this.storageService.getUser().roleId;
    }
  }



  onSubmitLogin(): void {
    const { email, password } = this.formlogin;
    this.userService.login(email, password).subscribe({
      next: data => {
        this.storageService.saveUser(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roleId = this.storageService.getUser().roleId;
        this.notifiService.openSnackBar('Đăng nhập thành công');

        if (this.storageService.getUser().role.roleType === 'ADMIN') {
          this.router.navigateByUrl('/admin/book');
        } else if (this.storageService.getUser().role.roleType === 'LECTURE') {
          this.router.navigateByUrl('/lecturer/class');
        } else {
          this.router.navigateByUrl('/home');
        }
        // this.reloadPage();

      },
      error: err => {
        console.log(err.status);
        this.notifiService.openSnackBar('Bạn nhập sai tài khoản hoặc mật khẩu. Vui lòng nhập lại!');
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
        if (err.status === 401) {
          this.notifiService.openSnackBar('Bạn nhập sai tài khoản hoặc mật khẩu. Vui lòng nhập lại!')
        }

      }
    })
  }


  //Register
  form: any = {
    nickName: null,
    firstName: null,
    lastName: null,
    memberId: null,
    // userName :null,
    email: null,
    password: null,
    rePassword: null,
    roleId: null
    // role: { roleId: null }
  };
  isSuccessful = false;
  isSignUpFailed = false;
  // errorMessage = '';
  // passwordMatch: boolean = true;
  passwordMatchError: boolean = false;
  onSubmit(): void {
    const { nickName,
      firstName,
      lastName,
      memberId,
      // userName,
      email,
      password,
      roleId
    } = this.form;
    if (password !== this.form.rePassword) {
      this.passwordMatchError = true;
      return;
    } else {
      this.passwordMatchError = false;
    }
    console.log('Role:', roleId);
    this.userService.register1(nickName,
      firstName,
      lastName,
      memberId,
      // userName,
      email,
      password,
      roleId)
      .subscribe({
        next: data => {
          console.log(data);
          this.isSuccessful = true;
          this.isSignUpFailed = false;
          // this.reloadPage();
          // this.notifiService.openSnackBar('Đăng ký thành công');
          this.router.navigateByUrl('/active-account');
        },
        error: err => {
          this.notifiService.openSnackBar('Có lỗi xáy ra trong khi đăng ký vui lòng kiểm tra lại');
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
          if (err.status === 409) {
            this.notifiService.openSnackBar('Email đã tồn tại vui lòng kiểm tra lại!')
          }
          if (err.status === 404) {
            this.notifiService.openSnackBar('Vui lòng nhập email theo đúng định dạng. Ex: abc@gmail.com');
          }
        }
      })
  }



  reloadPage(): void {
    window.location.reload();
  }


  ngAfterViewInit(): void {
    const registerButton = document.getElementById("register");
    const loginButton = document.getElementById("login");
    const container = document.getElementById("container");

    if (registerButton && loginButton && container) {
      registerButton.addEventListener("click", () => {
        container.classList.add("right-panel-active");
      });

      loginButton.addEventListener("click", () => {
        container.classList.remove("right-panel-active");
      });
    }
  }
  gotoRegister() {
    this.router.navigate(['register']);
  }

  forgotPassword() {
    this.router.navigate(['forgot-password']);
  }

}
