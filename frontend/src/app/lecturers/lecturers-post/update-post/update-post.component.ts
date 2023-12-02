import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { Post } from 'src/app/core/models/post';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LecPostService } from '../lec-post.service';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  id!: number;
  post: Post = new Post();
  currentUser: any;
  constructor(
    // private postService: PostService,
    private lecpostService: LecPostService,
    private route: ActivatedRoute,
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
    this.lecpostService.updatePost(this.id, this.post).subscribe( data =>{
      this.gotoPostList();
      this.nofiService.openSnackBar('Cập nhật bài viết thành công');
    })
  }
  gotoPostList(){
    this.currentUser = this.storageService.getUser();
    this.router.navigate(['/lecturer/'+ this.currentUser.userAccountId+'/post']);
  }
}
