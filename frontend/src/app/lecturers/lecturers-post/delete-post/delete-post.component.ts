import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/admin/manage-user/user.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LecPostService } from '../lec-post.service';

@Component({
  selector: 'app-delete-post',
  templateUrl: './delete-post.component.html',
  styleUrls: ['./delete-post.component.css']
})
export class DeletePostComponent implements OnInit{

  ngOnInit(): void {
    
  }

  constructor(
    private lecpostService: LecPostService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService,
    public dialogRef: MatDialogRef<DeletePostComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  deletePost(id: number) {
    this.lecpostService.deletePost(id).subscribe({
      next: () => {
        this.nofiService.openSnackBar('Đã xóa bài viết');
        // this.getPostbyUser();
      },
      error: () =>{
        this.nofiService.openSnackBar('Có lỗi xảy ra khi xóa bài viết');
      }
    })
  }
}
