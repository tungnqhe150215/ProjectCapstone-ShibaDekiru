import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { Post } from 'src/app/core/models/post';
import { NotificationService } from 'src/app/core/services/notification.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from '@angular/material/form-field';
import { LecPostService } from '../lec-post.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {FilePreviewService} from "../../../shared/services/file-preview.service";
@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit{

  post: Post = new Post();
  currentUser: any;
  file!: File ;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<CreatePostComponent>,
    private postService: PostService,
    private lecpostServive: LecPostService,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService,
    private storageService: StorageService,
  ){}
  ngOnInit(): void {

  }



  addPost(){

    if (this.file == null || this.file.size == 0) {
      this.post.image = "";
      this.currentUser = this.storageService.getUser();
      this.lecpostServive.addPost(this.currentUser.userAccountId, this.post).subscribe(data =>{
        console.log(data);
        this.nofiService.openSnackBar('Tạo bài viết thành công');
        this.dialogRef.close();
      })
    }

    else {
      this.fileService.uploadFile(this.file).subscribe(data => {
        this.drive = data as Drive
          this.post.image = this.drive.fileId
          this.currentUser = this.storageService.getUser();
          this.lecpostServive.addPost(this.currentUser.userAccountId, this.post).subscribe(data =>{
              console.log(data);
              this.nofiService.openSnackBar('Tạo bài viết thành công');
              this.dialogRef.close();
          })
      })
    }

  }


  createPost(){
    this.postService.createPost(this.post).subscribe( data =>{
      console.log(data);
      this.nofiService.openSnackBar('Tạo bài viết thành công');
      this.dialogRef.close();
    })
  }


  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if (element != null) {
      element.value = this.file.name;
    }
    if (this.file) {
      this.previewFile(this.file);
    }
  }

  previewFile(file: File) {
    const reader = new FileReader();

    reader.onload = () => {
      const preview = reader.result as string;
      this.filePreviewService.changePreview(preview);
    };

    // Read the file as a data URL
    reader.readAsDataURL(file);
  }
}
