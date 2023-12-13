import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UseServiceService } from '../use-service.service';
import { StorageService } from '../user-login/storage.service';
import { Subscription } from 'rxjs';
import { Student } from 'src/app/core/models/student';
import { ChangePassword } from 'src/app/core/models/change-password';
import { Lecture } from 'src/app/core/models/lecture';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {data} from "autoprefixer";


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

export class UserProfileComponent implements OnInit{


  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private fileService:FileService,
    private storageService: StorageService,
    private router: Router,
  ){}

  currentUser: any;
  isLoggedIn = false;
  eventBusSub?: Subscription;
  student: any;
  file!: File ;
  drive: Drive = new Drive();

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
    if(this.currentUser.role.roleType === 'LECTURE'){
      this.getLecturerByID();
    }else{
      this.getUserByID();
    }


  }

  getLecturerByID(){
    this.currentUser = this.storageService.getUser();
    this.student = new Lecture();
    this.userService.getLecturersbyID(this.currentUser.userAccountId)
    .subscribe(
      data =>{
        console.log(data);
        this.student = data;
      }
    )
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

  updateLecturersProfile(){

    if (this.file == null || this.file.size == 0) {

      this.student.avatar = "";
      this.userService.lecturerProfile(this.student).
      subscribe(
        data =>{
          console.log(data);
          this.getLecturerByID();
          this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
        },
        error1 =>  {
          console.error(error1);
          this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!')
        },
      );
    } else {
      this.fileService.uploadFileForMyProfile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.student.avatar = this.drive.fileId
        this.userService.lecturerProfile(this.student).
        subscribe(
          data =>{
            console.log(data);
            this.getLecturerByID();
            this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
          },
          error1 =>  {
            console.error(error1);
            this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!')
          },
        );
      })
    }

  }
  updateUserProfile(){
    if (this.file == null || this.file.size == 0) {

      this.student.avatar = "";
      this.userService.studentProfile(this.student).
      subscribe(
        data =>{
          console.log(data);
          this.getUserByID();
          this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
        },
        error1 =>  {
          console.error(error1);
          this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!')
        },
      );
    } else  {
      this.fileService.uploadFileForMyProfile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.student.avatar = this.drive.fileId
        this.userService.studentProfile(this.student).
        subscribe(
          data =>{
            console.log(data);
            this.getUserByID();
            this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
          },
          error1 =>  {
            console.error(error1);
            this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!')
          },
        );
      })
    }

  }



  changePassword() {
    // @ts-ignore
    this.updatePassword = new ChangePassword(this.form.password, this.form.newpassword, this.form.confirmpassword);

    this.userService.changePassword(this.updatePassword).subscribe(data => {
      console.log('dataPassword----->',data);

      this.notifiService.openSnackBar('Đổi mật khẩu thành công, bạn cần đăng nhập lại');
      this.logout();

    })

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


  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if(element != null) {
      element.value = this.file.name;
    }
  }

}
