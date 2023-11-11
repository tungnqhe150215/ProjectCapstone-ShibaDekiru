import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UseServiceService } from '../use-service.service';
import { StorageService } from '../user-login/storage.service';
import { Subscription } from 'rxjs';
import { Student } from 'src/app/core/models/student';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

export class UserProfileComponent implements OnInit{


  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
  ){}

  currentUser: any;
  isLoggedIn = false;
  eventBusSub?: Subscription;
  student: Student = new Student;
  // userAccountId!:number;
  // firstName !: string;
  // lastName !: string;
  // userName !: string;
  // memberId !: string;
  
  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    if(this.isLoggedIn){
      const user = this.storageService.getUser();
    }
    this.getUserByID();
  }

  getUserByID(){
    this.currentUser = this.storageService.getUser();
    this.student = new Student();
    this.userService.getStudentbyID(this.currentUser.userAccountId)
    .subscribe(data =>{
      console.log(data)
      this.student = data;
    })
  }

  updateUserProfile(){
    this.userService.studentProfile(this.student)
    .subscribe(
      data =>{
        console.log(data);
        this.getUserByID();
        this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
      }
    )
  }






}
