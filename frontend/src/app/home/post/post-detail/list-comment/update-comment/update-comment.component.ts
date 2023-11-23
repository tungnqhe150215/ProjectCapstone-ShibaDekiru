import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import { UserPostService } from '../../../user-post.service';
import { DeleteCommentComponent } from '../delete-comment/delete-comment.component';
import { Comment } from 'src/app/core/models/comment';

@Component({
  selector: 'app-update-comment',
  templateUrl: './update-comment.component.html',
  styleUrls: ['./update-comment.component.css']
})
export class UpdateCommentComponent implements OnInit{

  constructor(
    private userPostService: UserPostService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<UpdateCommentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private storageService: StorageService,
    
  ){}

  currentUser: any;
  ngOnInit(): void {

  }
  comment: Comment = new Comment();
  idC!:number;

  updateComment(){
    // this.idC = this.route.snapshot.params['id'];
    const idPost = this.userPostService.getPostID();
    this.currentUser = this.storageService.getUser();
    this.userPostService.updateComment(this.data, this.currentUser.userAccountId,idPost, this.comment).subscribe({
      next: (data) => {
        // this.goTolessonList();
        this.nofiService.openSnackBar('Cập nhật bình luận thành công');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Cập nhật bình luận thất bại vui lòng kiểm tra lại!');
      },
    }) 
  }
}
