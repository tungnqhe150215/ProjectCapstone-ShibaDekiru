import { Component, OnInit } from '@angular/core';
import { UserPostService } from '../user-post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/core/models/post';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit{

  id!:number;
  post: Post[] = [];
  constructor(
    private userPostService: UserPostService,
    private router: Router,
    private route: ActivatedRoute,
  ){}

  ngOnInit(): void {
    this.getAllPost();
  }

  getAllPost(){
    this.userPostService.getAllPost()
    .subscribe(data =>{
      this.post = data;
      console.log(data);
    })
  }

  PostDetail(id:number){
    this.userPostService.setPostID(id);
    this.router.navigate(['./post/post-detail',id]);
  }


}
