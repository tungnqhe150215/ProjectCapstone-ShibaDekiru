import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import { LecPostService } from '../lec-post.service';

@Component({
  selector: 'app-deletelec-comment',
  templateUrl: './deletelec-comment.component.html',
  styleUrls: ['./deletelec-comment.component.css']
})
export class DeletelecCommentComponent implements OnInit{

  constructor(
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeletelecCommentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private storageService: StorageService,
    private postLecService: LecPostService,
  ){}
  ngOnInit(): void {
    
  }
  id!:number;
  deleteComment(id: number){
    this.id = this.route.snapshot.params['id'];
    this.postLecService.deleteComment(this.id, this.data.userId, this.data.commentId)
    .subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Comment đã xóa!');
      },
      error: console.log,
    })
  }
}
