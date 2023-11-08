import { Component } from '@angular/core';
import { UseServiceService } from '../use-service.service';
import { StorageService } from '../user-login/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent {

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
  errorMessage = '';

  constructor(
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
  ){}

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
        this.router.navigateByUrl('/home');
      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    })
  }

  gotoLogin(){
    this.router.navigate(['login']);
  }



}

