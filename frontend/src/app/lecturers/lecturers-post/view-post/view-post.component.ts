import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';

import { Post } from 'src/app/core/models/post';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import { LecPostService } from '../lec-post.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit{
  id!:number;
  currentUser: any;
  post:Post = new Post;
  constructor(private route: ActivatedRoute, private postLecService: LecPostService, private router:Router,  private storageService: StorageService,){

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.post = new Post();
    this.postLecService.getPostByID(this.id).subscribe(data =>{
      this.post = data
    })
    
  }

  goToPost(){
    this.currentUser = this.storageService.getUser();
    this.router.navigate(['/lecturer/'+ this.currentUser.userAccountId+'/post']);
  }

}
