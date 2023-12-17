import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { Post } from 'src/app/core/models/post';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LecPostService } from '../lec-post.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {FilePreviewService} from "../../../shared/services/file-preview.service";

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  id!: number;
  post: Post = new Post();
  currentUser: any;
  file!: File;
  drive: Drive = new Drive();

  constructor(
    // private postService: PostService,
    private lecpostService: LecPostService,
    private route: ActivatedRoute,
    private fileService: FileService,
    private filePreviewService: FilePreviewService,
    private router: Router,
    private nofiService: NotificationService,
    private storageService: StorageService,
  ) { }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.lecpostService.getPostByID(this.id).subscribe(data =>{
      this.post = data;
    },error => console.log(error));
  }

  onSubmit(){
    if (this.file == null || this.file.size == 0) {
      this.post.image = "";
      this.lecpostService.updatePost(this.id, this.post).subscribe( data =>{
        this.gotoPostList();
        this.nofiService.openSnackBar('Cập nhật bài viết thành công');
      })
    }
    else {
      this.fileService.uploadFile(this.file).subscribe(data => {
        this.drive = data as Drive
        this.post.image = this.drive.fileId
        this.lecpostService.updatePost(this.id, this.post).subscribe( data =>{
          this.gotoPostList();
          this.nofiService.openSnackBar('Cập nhật bài viết thành công');
        })
      })
    }

  }
  gotoPostList(){
    this.currentUser = this.storageService.getUser();
    this.router.navigate(['/lecturer/'+ this.currentUser.userAccountId+'/post']);
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
