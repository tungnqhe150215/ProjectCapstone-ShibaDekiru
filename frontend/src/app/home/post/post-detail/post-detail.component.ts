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
import {UserAccount} from "../../../core/models/user-account";
import {UseServiceService} from "../../auth/use-service.service";
import {Lecture} from "../../../core/models/lecture";
import {data} from "autoprefixer";

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
  totalPages: number[] = [];

  currentPage = 0;
  pageSize = 5;
  keyword!: string;
  lecture: Lecture = new Lecture();
  totalElements: number = 0;
  constructor(
    private userPostService: UserPostService,
    private userService: UseServiceService,
    private router: Router,
    private route: ActivatedRoute,
    private studentLessonService: StudentLessonService,
    private storageService: StorageService,
    private nofiService: NotificationService,
    public dialog: MatDialog
  ) { }
  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.getPostDetailByID();
     this.getAllComment();
     this.getAllPost();

  }

  getPostDetailByID(){
    this.id = this.route.snapshot.params['id'];
    this.post = new Post();
    this.userPostService.getPostByID(this.id).subscribe(data => {
      this.post = data;
      console.log(this.post.lectureId)
      this.userService.getLectureByUserId(this.post.lectureId).subscribe(data => {
            this.lecture = data
        this.getAllComment()
      }
      )
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
      console.log(this.userId)
      this.userPostService.createComment(this.id, this.currentUser.userId, this.uComment)
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
    }).afterClosed().subscribe( () => { this.getAllComment()
    this.updatePageAfterDelete()
    this.getPostDetailByID()});
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

    this.userPostService.deleteComment(this.id, this.currentUser.userId, idC)
      .subscribe({
        next: (res) => {
          this.nofiService.openSnackBar('Comment đã xóa!');
          this.getPostDetailByID();
          // window.location.reload();
        },
        error: (err) => {
          console.error(err);
          this.nofiService.openSnackBar('Có lỗi xảy ra khi xóa comment. Vui lòng thử lại!', 'Cancel');
        },
      });
  }




  getAllComment() {
    this.id = this.route.snapshot.params['id'];
    this.currentUser  = this.storageService.getUser();

    if (this.currentPage >= this.totalPages.length) {
      this.currentPage = Math.max(0, this.totalPages.length - 1);
    }

    this.userPostService.getAllComment(this.id, this.currentPage, this.pageSize)
      .subscribe(data => {
        this.comment = data.content;
        this.totalElements = data.totalElements;
        this.totalPages = Array.from({ length: data.totalPages }, (_, index) => index + 1);
        console.log(data);
      })
  }

  onPageChange(newPage: number) {
    if (newPage >= 0 && newPage < this.totalPages.length) {
      this.currentPage = newPage;
      this.getAllComment();
    }

  }

  onNextPage() {
    // if (this.currentPage < this.totalPages.length - 1) {
    //   this.onPageChange(this.currentPage + 1);
    // }
    if ((this.currentPage + 1) * this.pageSize < this.totalElements) {
      this.onPageChange(this.currentPage + 1);
    }
  }

  onPreviousPage() {
    if (this.currentPage > 0) {
      this.onPageChange(this.currentPage - 1);
    }
  }

  updatePageAfterDelete() {
    if (this.totalElements > 0) {

      const newTotalPages = Math.ceil(this.totalElements / this.pageSize);


      if (newTotalPages < this.totalPages.length) {
        this.currentPage = Math.max(0, newTotalPages - 1);
      }

      this.totalPages = Array.from({ length: newTotalPages }, (_, index) => index);
    } else {
      this.currentPage = 0;
      this.totalPages = [];
    }
  }
  getAllPost(){
    this.userPostService.getAllPost(this.currentPage, this.pageSize, this.keyword)
      .subscribe((page) =>{
        this.post1 = page.content;
        this.totalPages = Array.from({ length: page.totalPages }, (_, index) => index);
        this.updatePageAfterDelete();
      })
  }



  backTohome(){
    this.router.navigate(['/login']);
  }
}
