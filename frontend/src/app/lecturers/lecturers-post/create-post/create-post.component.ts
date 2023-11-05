import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { Post } from 'src/app/core/models/post';
import { NotificationService } from 'src/app/core/services/notification.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from '@angular/material/form-field';
import { LecPostService } from '../lec-post.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit{

  post: Post = new Post();
  myID: number = 1;
  currentUser: any;
  constructor(
    public dialogRef: MatDialogRef<CreatePostComponent>,
    private postService: PostService,
    private lecpostServive: LecPostService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService,
    private storageService: StorageService,
  ){}
  ngOnInit(): void {
    
  }



  addPost(){
    this.currentUser = this.storageService.getUser();
    this.lecpostServive.addPost(this.currentUser.lectureId, this.post).subscribe(data =>{
      console.log(data);
      this.dialogRef.close();
    })
  }


  createPost(){
    this.postService.createPost(this.post).subscribe( data =>{
      console.log(data);
      this.nofiService.openSnackBar('Create Post successful', 'OK');
      this.dialogRef.close();
    })
  }


}
