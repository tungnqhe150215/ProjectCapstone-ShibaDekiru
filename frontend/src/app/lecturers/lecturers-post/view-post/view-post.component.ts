import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';

import { Post } from 'src/app/core/models/post';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit{
  id!:number;
  post:Post = new Post;
  constructor(private route: ActivatedRoute, private postService: PostService, private router:Router, ){

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.post = new Post();
    this.postService.getPostByID(this.id).subscribe(data =>{
      this.post = data
    })
    
  }

  goToPost(){
    this.router.navigate(['/lecturer/3/post']);
  }

}
