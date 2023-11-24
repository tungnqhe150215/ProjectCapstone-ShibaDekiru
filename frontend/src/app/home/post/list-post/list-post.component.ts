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

  // Thêm biến cho phân trang
  p: number = 1;
  itemsPerPage: number = 5;
  collectionSize: number = 0;
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
      this.collectionSize = this.post.length;
      console.log(data);
    })
  }

  get totalPages(): number {
    return Math.ceil(this.collectionSize / this.itemsPerPage);
  }

  get pages(): number[] {
    const totalPages = this.totalPages;
    return Array.from({ length: totalPages }, (_, index) => index + 1);
  }

  PostDetail(id:number){
    this.userPostService.setPostID(id);
    this.router.navigate(['./post/post-detail',id]);
  }

  previousPage(event: Event) {
    event.preventDefault();
    if (this.p > 1) {
      this.p--;
    }
  }
  
  nextPage(event: Event) {
    event.preventDefault();
    if (this.p < this.totalPages) {
      this.p++;
    }
  }
  
  goToPage(page: number, event: Event) {
    event.preventDefault();
    this.p = page;
  }
  
}
