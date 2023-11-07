import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserPostService } from '../user-post.service';
import { Post } from 'src/app/core/models/post';
import { Comment } from 'src/app/core/models/comment';
import { StudentLessonService } from '../../lesson/student-lesson.service';
import { StorageService } from '../../auth/user-login/storage.service';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {

  id!: number;
  post: Post = new Post;
  comment: Comment[] = [];
  isLoggedIn = false;
  nickName!: string; 
  userId !:number;
  constructor(
    private userPostService: UserPostService,
    private router: Router,
    private route: ActivatedRoute,
    private studentLessonService: StudentLessonService,
    private storageService: StorageService,
    private nofiService: NotificationService,
  ) { }
  ngOnInit(): void {
    
    this.getPostDetailByID();
     this.getAllComment();
  }
  
  getPostDetailByID(){
    this.id = this.route.snapshot.params['id'];
    this.post = new Post();
    this.userPostService.getPostByID(this.id).subscribe(data => {
      this.post = data;
      console.log(data);
    })
  }

  currentUser: any;
  uComment: Comment = new Comment;

  

  AddComment() {
    this.id = this.route.snapshot.params['id'];
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      this.userPostService.createComment(this.id, this.currentUser.userAccountId, this.uComment)
      .subscribe(
        data => {
          console.log(data);
          this.nofiService.openSnackBar('Comment thành công', 'Ok');
          this.getAllComment();
          // this.getPostDetailByID();
          // window.location.reload();
        }
      )
      
    } else {
      this.router.navigate(['/home']);
      this.nofiService.openSnackBar('Bạn hãy đăng nhập đã', 'Ok');
    }
  }

  deleteComment(idC: number){
    this.id = this.route.snapshot.params['id'];
    this.currentUser = this.storageService.getUser();
    this.userPostService.deleteComment(this.id, this.currentUser.userAccountId, idC)
    .subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Comment đã xóa!', 'Ok');
        this.getAllComment();
        // window.location.reload();
      },
      error: console.log,
    })
  }


  getAllComment() {
    this.id = this.route.snapshot.params['id'];
    this.currentUser  = this.storageService.getUser();
    this.nickName = this.currentUser.nickName;
    this.userId = this.currentUser.userAccountId;
    this.userPostService.getAllComment(this.id)
      .subscribe(data => {
        this.comment = data;
        console.log(data);
      })
  }

}
