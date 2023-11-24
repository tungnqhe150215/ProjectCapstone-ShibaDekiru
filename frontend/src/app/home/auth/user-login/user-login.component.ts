import { Component, OnInit , AfterViewInit} from '@angular/core';
import { UseServiceService } from '../use-service.service';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit, AfterViewInit{


  
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
  ){}


  ngOnInit(): void {
    if(this.storageService.isLoggedIn()){
      this.isLoggedIn = true;
      this.roleId = this.storageService.getUser().roleId;
    }
  }



  onSubmitLogin(): void{
    const { email, password } = this.formlogin;
    this.userService.login(email, password).subscribe({
      next: data =>{
        this.storageService.saveUser(data);
        this.isLoginFailed=false;
        this.isLoggedIn = true;
        this.roleId = this.storageService.getUser().roleId;
        this.notifiService.openSnackBar('Đăng nhập thành công');
        
        if(this.storageService.getUser().role.roleType === 'ADMIN'){
          this.router.navigateByUrl('/admin/book');
        }else if(this.storageService.getUser().role.roleType === 'LECTURE'){
          this.router.navigateByUrl('/lecturer/class');
        }else{
          this.router.navigateByUrl('/home');
        }
        // this.reloadPage();

      },
      error: err =>{
        this.notifiService.openSnackBar('Có lỗi xáy ra trong quá trình đăng nhập vui lòng kiểm tra lại');
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    })
  }


  //Register
  form: any = {
    nickName :null,
    firstName :null,
    lastName :null,
    memberId :null,
    userName :null,
    email :null,
    password :null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  // errorMessage = '';

  onSubmit(): void {
    const {nickName,
      firstName,
      lastName,
      memberId,
      userName,
      email,
      password 
    } = this.form;
    
    this.userService.register(nickName,
      firstName,
      lastName,
      memberId,
      userName,
      email,
      password)
      .subscribe({
      next: data =>{
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        // this.reloadPage();
        // this.notifiService.openSnackBar('Đăng ký thành công');
        this.router.navigateByUrl('/active-account');
      },
      error: err =>{
        this.notifiService.openSnackBar('Có lỗi xáy ra trong khi đăng ký vui lòng kiểm tra lại');
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
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
  gotoRegister(){
    this.router.navigate(['register']);
  }

  forgotPassword(){
    this.router.navigate(['forgot-password']);
  }

}
