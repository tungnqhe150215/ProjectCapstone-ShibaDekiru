import { Component, Inject, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/core/models/post';
import { UserPostService } from '../../user-post.service';
import { Comment } from 'src/app/core/models/comment';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-list-comment',
  templateUrl: './list-comment.component.html',
  styleUrls: ['./list-comment.component.css']
})
export class ListCommentComponent implements OnInit{
  id:number = 2;
  post: Post = new Post;
  comment: Comment[]=[];
  constructor(
    private userPostService: UserPostService,
    private router: Router,
    private route: ActivatedRoute,
  ){}

  ngOnInit(): void {
   this.getAllComment();
  }

  getAllComment(){
    this.userPostService.getAllComment(this.id)
    .subscribe( data =>{
      this.comment = data;
      console.log(data);
    })
  }
}
