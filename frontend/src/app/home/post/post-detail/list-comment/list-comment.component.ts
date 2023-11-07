import { Component, Inject, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/core/models/post';
import { UserPostService } from '../../user-post.service';
import { Comment } from 'src/app/core/models/comment';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificationService } from 'src/app/core/services/notification.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
@Component({
  selector: 'app-list-comment',
  templateUrl: './list-comment.component.html',
  styleUrls: ['./list-comment.component.css']
})
export class ListCommentComponent implements OnInit{
  id:number = 1;
  idC!: number;
  post: Post = new Post;
  comment: Comment[]=[];
  constructor(
    private userPostService: UserPostService,
    private router: Router,
    private route: ActivatedRoute,
    private storageService: StorageService,
    private nofiService: NotificationService,
  ){}
  currentUser: any;

  ngOnInit(): void {
   this.getAllComment();
  }

  getAllComment(){
    this.idC = this.route.snapshot.params['id'];
    this.userPostService.getAllComment(this.idC)
    .subscribe( data =>{
      this.comment = data;
      console.log(data);
    })
  }

  
  deleteComment(idC: number){
    this.id = this.route.snapshot.params['id'];
    this.currentUser = this.storageService.getUser();
    this.userPostService.deleteComment(this.id, this.currentUser.userAccountId, idC)
    .subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Comment đã xóa!', 'Ok');
        window.location.reload();
      },
      error: console.log,
    })
  }
}
