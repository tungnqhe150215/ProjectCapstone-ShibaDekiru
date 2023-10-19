import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../post.service';
import { Post } from 'src/app/core/models/post';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit{

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
    this.router.navigate(['admin/post']);
  }
}
