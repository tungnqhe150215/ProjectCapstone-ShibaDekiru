import {Component, OnInit} from '@angular/core';
import {UserPostService} from '../user-post.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Post} from 'src/app/core/models/post';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {

  id!: number;
  post: Post[] = [];
  posts: Post[] = [];
  totalPages: number[] = [];

  currentPage = 0;
  pageSize = 5;
  keyword: string = "";
  searchPerformed: boolean = false;
  constructor(
    private userPostService: UserPostService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.getAllPost();
    this.getLatestPost();
  }

  getAllPost() {

    this.userPostService.getAllPost(this.currentPage, this.pageSize, this.keyword)
      .subscribe((page) => {
        this.post = page.content;
        this.totalPages = Array.from({length: page.totalPages}, (_, index) => index);

      })


  }

  onPageChange(newPage: number) {
    if (newPage >= 0 && newPage < this.totalPages.length) {
      this.currentPage = newPage;
      this.getAllPost();
    }
  }

  onNextPage() {
    if (this.currentPage < this.totalPages.length - 1) {
      this.onPageChange(this.currentPage + 1);
    }
  }

  onPreviousPage() {
    if (this.currentPage > 0) {
      this.onPageChange(this.currentPage - 1);
    }
  }


  PostDetail(id: number) {
    this.userPostService.setPostID(id);
    this.router.navigate(['./post/post-detail', id]);
  }


  getLatestPost() {
    this.userPostService.getLatestPost()
      .subscribe(data => {
        this.posts = data;
        console.log(data);
      })
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.keyword = filterValue;
    if (this.post.length === 0) {
      this.searchPerformed = true;

    }
    this.getAllPost();
  }
}
