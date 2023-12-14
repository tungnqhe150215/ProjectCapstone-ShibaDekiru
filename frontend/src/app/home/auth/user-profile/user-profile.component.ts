import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NotificationService} from 'src/app/core/services/notification.service';
import {UseServiceService} from '../use-service.service';
import {StorageService} from '../user-login/storage.service';
import {Subscription} from 'rxjs';
import {Student} from 'src/app/core/models/student';
import {ChangePassword} from 'src/app/core/models/change-password';
import {Lecture} from 'src/app/core/models/lecture';
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {FilePreviewService} from "../../../shared/services/file-preview.service";
import {UserAccount} from "../../../core/models/user-account";


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

export class UserProfileComponent implements OnInit {


  constructor(
    private notifiService: NotificationService,
    private userService: UseServiceService,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private storageService: StorageService,
    private router: Router,
  ) {
  }

  currentUser: any;
  isLoggedIn = false;
  eventBusSub?: Subscription;
  student: any;
  file!: File;
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
  nickName !: string;
  userAccount: UserAccount = new UserAccount();
  // userAccountId!:number;
  // firstName !: string;
  // lastName !: string;
  // userName !: string;
  // memberId !: string;

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.getUserAccountByMemberId()
    }
    if (this.currentUser.role.roleType === 'LECTURE') {
      this.getLecturerByID();
    } else {
      this.getUserByID();
    }


  }

  getLecturerByID() {
    this.currentUser = this.storageService.getUser();
    this.student = new Lecture();
    this.userService.getLecturersbyID(this.currentUser.userAccountId)
      .subscribe(
        data => {
          console.log(data);
          this.student = data;
        }
      )
  }

  getUserByID() {
    this.currentUser = this.storageService.getUser();
    this.student = new Student();
    this.userService.getStudentbyID(this.currentUser.userAccountId)
      .subscribe(data => {
        console.log(data)
        this.student = data;
      })
  }

  getUserAccountByMemberId() {
    this.currentUser = this.storageService.getUser();
    this.userService.getUserAccountByUserId(this.currentUser.userId).subscribe(data => {
        this.userAccount = data
      }
    )
  }

  updateLecturersProfile() {

    this.userService.updateUserAccount(this.userAccount.userAccountId, this.userAccount.nickName).subscribe(data => {
      console.log(data);
    })

    if (this.file == null || this.file.size == 0) {

      this.student.avatar = "";
      this.userService.lecturerProfile(this.student).subscribe(
        data => {
          console.log(data);
          this.getLecturerByID();
          this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
        },
        error1 => {
          this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!');
          if (error1.status == 409) {
            this.notifiService.openSnackBar('Số điện thoại đã tồn tại, vui lòng kiểm tra lại!');
          }
        },
      );
    } else {
      this.fileService.uploadFileForMyProfile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.student.avatar = this.drive.fileId
        this.userService.lecturerProfile(this.student).subscribe(
          data => {
            console.log(data);
            this.getLecturerByID();
            this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
          },
          error1 => {
            this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!');
            if (error1.status == 409) {
              this.notifiService.openSnackBar('Số điện thoại đã tồn tại, vui lòng kiểm tra lại!');
            }
          },
        );
      })
    }

  }

  updateUserProfile() {

    this.userService.updateUserAccount(this.userAccount.userAccountId, this.userAccount.nickName).subscribe(data => {
      console.log(data);
    })

    if (this.file == null || this.file.size == 0) {

      this.student.avatar = "";
      this.userService.studentProfile(this.student).subscribe(
        data => {
          console.log(data);
          this.getUserByID();
          this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
        },
        error1 => {
          this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!');
          if (error1.status == 409) {
            this.notifiService.openSnackBar('Số điện thoại đã tồn tại, vui lòng kiểm tra lại!');
          }
        },
      );
    } else {
      this.fileService.uploadFileForMyProfile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.student.avatar = this.drive.fileId
        this.userService.studentProfile(this.student).subscribe(
          data => {
            console.log(data);
            this.getUserByID();
            this.notifiService.openSnackBar('Cập nhật thông tin thành công !');
          },
          error1 => {
            this.notifiService.openSnackBar('Đã xảy ra lỗi khi cập nhật thông tin người dùng!');
            if (error1.status == 409) {
              this.notifiService.openSnackBar('Số điện thoại đã tồn tại, vui lòng kiểm tra lại!');
            }
          },
        );
      })
    }

  }


  changePassword() {
    // @ts-ignore
    this.updatePassword = new ChangePassword(this.form.password, this.form.newpassword, this.form.confirmpassword);

    this.userService.changePassword(this.updatePassword).subscribe(data => {
      console.log('dataPassword----->', data);

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
    if (element != null) {
      element.value = this.file.name;
    }
  }

}
