import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { Post } from 'src/app/core/models/post';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  id!: number;
  post: Post = new Post();
  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService
  ) { }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.postService.getPostByID(this.id).subscribe(data =>{
      this.post = data;
    },error => console.log(error));
  }

  onSubmit(){
    this.postService.updatePost(this.id, this.post).subscribe( data =>{
      this.gotoPostList();
      this.nofiService.openSnackBar('Update successful !', 'Cancel');
    })
  }
  gotoPostList(){
    this.router.navigate(['lecturer']);
  }
}
