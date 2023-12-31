import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { UseServiceService } from '../auth/use-service.service';
import { StorageService } from '../auth/user-login/storage.service';
import { EventBusService } from '../auth/event-bus.service';
import { Subscription } from 'rxjs';
import { StudentLessonService } from '../lesson/student-lesson.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Book } from 'src/app/core/models/book';
import {Lecture} from "../../core/models/lecture";
import {data} from "autoprefixer";
import {Student} from "../../core/models/student";

@Component({
  selector: 'home-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css','../home-style.css']
})
export class HeaderComponent implements AfterViewInit,OnInit{

  @ViewChild('header') elementView!: ElementRef<HTMLInputElement>;

  contentHeight !: number;
  scrollNumber !:number;
  userAccountId!:number;
  isLoggedIn = false;
  roleId!: number;
  userName?:string;
  eventBusSub?: Subscription;
  book: Book[]= [];
  lecture: Lecture = new Lecture();
  student: Student = new Student();
  constructor(
    private userService: UseServiceService,
    private storageService: StorageService,
    private router: Router,
    private eventBusService: EventBusService,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
  ) {}

  ngAfterViewInit(): void {
    function menuToggle(): void {
      const toggleMenu: HTMLElement | null = document.querySelector('.menu');
      toggleMenu?.classList.toggle('active');
    }
  }
  currentUser: any;

  getImageByLectureId(){
    this.currentUser = this.storageService.getUser();
    if (this.currentUser.role.roleType === 'LECTURE') {
      this.userService.getImageLectureByUserId(this.currentUser.userAccountId).subscribe(data => {
        this.lecture = data
      })
    }

  }

  getImageByStudentId(){

    this.currentUser = this.storageService.getUser();
    if (this.currentUser.role.roleType === 'STUDENT') {
      this.userService.getImageStudentByd(this.currentUser.userAccountId).subscribe(data => {
        this.student = data
      })
    }

  }

  ngOnInit(): void {
    this.getAllBook();
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();

    if(this.isLoggedIn){
      const user = this.storageService.getUser();
      this.userAccountId = user.userAccountId;
      this.roleId = user.roleId;
      this.userName = user.userName;
      this.getImageByLectureId();
      this.getImageByStudentId();
    }
    this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logout();
    });
  }
  // logout(): void{
  //   this.userService.logout().subscribe({
  //     next: res =>{
  //       console.log(res);
  //       this.storageService.clean();
  //       this.router.navigateByUrl('/home');

  //     },
  //     error: err =>{
  //       console.log(err);
  //     }
  //   })
  // }
  logout(): void {
    this.storageService.clean();
    this.userService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();
        this.router.navigate(['']);
        // window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }

  menuToggle() {
    const toggleMenu: HTMLElement | null = document.querySelector('.menu');
    if (toggleMenu) {
      toggleMenu.classList.toggle('active');
    }
  }



  getAllBook(){
   this.studentLessonService.getAllBook()
   .subscribe( data =>{
    this.book = data;
    console.log(data);

   })
  }

  LessonByBook(id:number){
    this.studentLessonService.setBookId(id);
    this.router.navigate(['book/'+id+'/lesson']);
  }
}
