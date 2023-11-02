import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserPostService } from '../user-post.service';
import { Post } from 'src/app/core/models/post';
import { Comment } from 'src/app/core/models/comment';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit{

  id!:number;
  post: Post = new Post;
  comment: Comment[]=[];
  constructor(
    private userPostService: UserPostService,
    private router: Router,
    private route: ActivatedRoute,
  ){}
  ngOnInit(): void {
   this.id = this.route.snapshot.params['id'];
   this.post= new Post();
   this.userPostService.getPostByID(this.id).subscribe(data =>{
    this.post = data;
    console.log(data);
   })

  //  this.getAllComment();
  }

  getAllComment(){
    this.userPostService.getAllComment(this.id)
    .subscribe( data =>{
      this.comment = data;
      console.log(data);
    })
  }

}
