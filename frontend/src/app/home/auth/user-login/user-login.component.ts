import { Component, OnInit , AfterViewInit} from '@angular/core';
import { UseServiceService } from '../use-service.service';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit, AfterViewInit{

  form: any = {
    email: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roleId!: number;
  constructor(
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


  onSubmit(): void{
    const { email, password } = this.form;
    this.userService.login(email, password).subscribe({
      next: data =>{
        this.storageService.saveUser(data);
        this.isLoginFailed=false;
        this.isLoggedIn = true;
        this.roleId = this.storageService.getUser().roleId;
        this.router.navigateByUrl('/home');
        // this.reloadPage();

      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
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




}
