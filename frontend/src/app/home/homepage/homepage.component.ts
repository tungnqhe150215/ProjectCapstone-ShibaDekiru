import { Component, OnInit } from '@angular/core';
import { UseServiceService } from '../auth/use-service.service';
import { StorageService } from '../auth/user-login/storage.service';
import { StudentLessonService } from '../lesson/student-lesson.service';
import { Router } from '@angular/router';
import { Book } from 'src/app/core/models/book';
import { UserPostService } from '../post/user-post.service';
import { Post } from 'src/app/core/models/post';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css','../home-style.css']
})
export class HomepageComponent implements OnInit{
  book: Book[]= [];
  post: Post[]=[];
  ngOnInit(): void {
    this.getAllBook();
    this.getLatestPost();
  }
  constructor(
    private userPostService: UserPostService,
    private studentLessonService: StudentLessonService,
    private router: Router,
  ){}
  getAllBook(){
    this.studentLessonService.getAllBook()
    .subscribe( data =>{
     this.book = data;
     console.log(data);
 
    })  
   }

   PostDetail(id:number){
    this.userPostService.setPostID(id);
    this.router.navigate(['./post/post-detail',id]);
  }
  
   getLatestPost(){
    this.userPostService.getLatestPost()
    .subscribe(data =>{
      this.post = data;
      console.log(data);
    })
   }
 
   LessonByBook(id:number){
     this.studentLessonService.setBookId(id);
     this.router.navigate(['book/'+id+'/lesson']);
   }  
  // isLoggedIn = false;
  // // showAdminBoard = false;
  // // showModeratorBoard = false;
  // username?: string;
  // constructor(
  //   private userService: UseServiceService,
  //   private storageService: StorageService,
  // ){
    
  // }
  // ngOnInit(): void {
  //   this.isLoggedIn = this.storageService.isLoggedIn();

  //   if(this.isLoggedIn){
  //     const user = this.storageService.getUser();
  //   this.username = user.username;
  //   }
  //   this.logout();
  // }

  // logout(): void{
  //   this.userService.logout().subscribe({
  //     next: res =>{
  //       console.log(res);
  //       this.storageService.clean();
  //       window.location.reload();
  //     },
  //     error: err=>{
  //       console.log(err);
  //     }
  //   });
  // }
}
