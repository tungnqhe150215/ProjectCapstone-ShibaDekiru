import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserPostService } from '../user-post.service';
import { Post } from 'src/app/core/models/post';
import { Comment } from 'src/app/core/models/comment';
import { StudentLessonService } from '../../lesson/student-lesson.service';
import { StorageService } from '../../auth/user-login/storage.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteCommentComponent } from './list-comment/delete-comment/delete-comment.component';
import { UpdateCommentComponent } from './list-comment/update-comment/update-comment.component';

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
    public dialog: MatDialog
  ) { }
  ngOnInit(): void {
    
    this.getPostDetailByID();
     this.getAllComment();
     this.getAllPost();
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
  post1: Post[] = [];
  idPost!:number;

  // AddComment() {
  //   this.id = this.route.snapshot.params['id'];
  //   this.currentUser = this.storageService.getUser();
  //   this.isLoggedIn = this.storageService.isLoggedIn();
  //   if (this.isLoggedIn) {
  //     this.userPostService.createComment(this.id, this.currentUser.userAccountId, this.uComment)
  //     .subscribe(
  //       data => {
  //         console.log(data);
  //         this.nofiService.openSnackBar('Comment thành công');
  //         this.getAllComment();
  //         // this.getPostDetailByID();
  //         // window.location.reload();
  //       }
  //     )
      
  //   } else {
  //     this.router.navigate(['/home']);
  //     this.nofiService.openSnackBar('Bạn hãy đăng nhập đã');
  //   }
  // }
  AddComment() {
    this.id = this.route.snapshot.params['id'];
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    
    if (this.isLoggedIn) {
      this.userPostService.createComment(this.id, this.currentUser.userAccountId, this.uComment)
        .subscribe(
          data => {
            console.log(data);
            this.nofiService.openSnackBar('Comment thành công');
            this.getAllComment();
            // this.getPostDetailByID();
            // window.location.reload();
          },
          error => {
            console.error(error);
            this.nofiService.openSnackBar('Có lỗi xảy ra khi thêm comment. Vui lòng thử lại!');
          }
        );
    } else {
      this.router.navigate(['/home']);
      this.nofiService.openSnackBar('Bạn hãy đăng nhập đã');
    }
  }
  

  // PostDetail(id:number){
  //   this.id = this.route.snapshot.params['id'];
  //   this.userPostService.setPostID(id);
  //   this.router.navigate(['./post/post-detail',id]);
  // }

  openDeleteComment(idC:number){
    this.dialog.open(DeleteCommentComponent, {
      data:idC
    }).afterClosed().subscribe( () => this.getAllComment())
  }

  openUpdateComment(idC:number, cid:number){
    this.userPostService.setPostID(cid);
    this.dialog.open(UpdateCommentComponent, {
      data:idC
    },
    ).afterClosed().subscribe( () => this.getAllComment())
  }


  // deleteComment(idC: number){
  //   this.id = this.route.snapshot.params['id'];
  //   this.currentUser = this.storageService.getUser();
  //   this.userPostService.deleteComment(this.id, this.currentUser.userAccountId, idC)
  //   .subscribe({
  //     next: (res) => {
  //       this.nofiService.openSnackBar('Comment đã xóa!');
  //       this.getAllComment();
  //       // window.location.reload();
  //     },
  //     error: console.log,
  //   })
  // }
  deleteComment(idC: number) {
    this.id = this.route.snapshot.params['id'];
    this.currentUser = this.storageService.getUser();
  
    this.userPostService.deleteComment(this.id, this.currentUser.userAccountId, idC)
      .subscribe({
        next: (res) => {
          this.nofiService.openSnackBar('Comment đã xóa!');
          this.getAllComment();
          // window.location.reload();
        },
        error: (err) => {
          console.error(err);
          this.nofiService.openSnackBar('Có lỗi xảy ra khi xóa comment. Vui lòng thử lại!', 'Cancel');
        },
      });
  }
  


  p: number = 1;
  itemsPerPage: number = 5; // Số lượng bình luận trên mỗi trang
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

  getAllPost(){
    this.userPostService.getAllPost()
    .subscribe(data =>{
      this.post1 = data;
      console.log(data);
    })
  }

}
