import {Component, OnInit} from '@angular/core';
import { faTrash} from "@fortawesome/free-solid-svg-icons";
import {Class} from "../../../core/models/class";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentClassService} from "../student-class.service";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {data} from "autoprefixer";
import {ClassStudent} from "../../../core/models/class-student";
import { StorageService } from '../../auth/user-login/storage.service';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-class-list',
  templateUrl: './class-list.component.html',
  styleUrls: ['./class-list.component.css','../../home-style.css']
})
export class ClassListComponent implements OnInit{
  trash = faTrash;
  joinedClass: ClassStudent[] = [];
  joinC: Class = new Class()
  userId!: number;
  code!: string;
  currentUser: any;
  isLoggedIn = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private studentClass: StudentClassService,
              private sessionStorage: SessionStorageService,
              private storageService: StorageService,
              private nofiService: NotificationService,) {
  }

  ngOnInit(): void {
    this.getListClass()
  }

  getListClass(){
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    if(this.isLoggedIn){
    this.userId = this.sessionStorage.getJsonData("auth-user").userAccountId
    this.studentClass.getClassByStudent(this.userId).subscribe(data => {
      this.joinedClass = data
      console.log(data)
    })}
    else{
      this.router.navigate(['/home']);
      this.nofiService.openSnackBar('Bạn hãy đăng nhập đã');
    }
  }

  joinClass(){
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    if(this.isLoggedIn){
    this.studentClass.joinClassByClassCode(this.userId,this.code).subscribe(data => {
      console.log(data)
      this.joinC = data as Class
      this.goToClass(this.joinC.classId)
    })}
    else{
      this.router.navigate(['/home']);
      this.nofiService.openSnackBar('Bạn hãy đăng nhập đã');
    }
  }

  goToClass(classId:number){
    this.router.navigate(['class',classId])
  }

}
