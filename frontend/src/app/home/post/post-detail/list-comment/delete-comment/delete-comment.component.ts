import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { DeleteLessonComponent } from 'src/app/admin/manage-lesson/delete-lesson/delete-lesson.component';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UserPostService } from '../../../user-post.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';

@Component({
  selector: 'app-delete-comment',
  templateUrl: './delete-comment.component.html',
  styleUrls: ['./delete-comment.component.css']
})
export class DeleteCommentComponent implements OnInit{

  constructor(
    private userPostService: UserPostService,
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteCommentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private storageService: StorageService,
    
  ){}
  currentUser: any;
  ngOnInit(): void {

  }
  idC!:number;

  deleteComment(idC: number){
    this.idC = this.route.snapshot.params['id'];
    this.currentUser = this.storageService.getUser();
    this.userPostService.deleteComment(this.idC, this.currentUser.userAccountId, this.data)
    .subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Comment đã xóa!', 'Ok');
      },
      error: console.log,
    })
  }


}
