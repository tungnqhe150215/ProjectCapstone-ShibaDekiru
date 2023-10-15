import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/core/models/post';
import { PostService } from '../post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit{
  post: Post[] = [];

  constructor(private postService: PostService, private router: Router){
    
  }

  ngOnInit(): void {
    this.GetPost();
  }
  private GetPost(){
    this.postService.getPostlist().subscribe(data=>{
      this.post = data;
    });
  }

  postDetail(id:number){
    this.router.navigate(['post-detail',id]);
  }
}
