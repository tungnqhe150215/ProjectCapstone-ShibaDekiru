import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { Comment } from 'src/app/core/models/comment';
import { Post } from 'src/app/core/models/post';
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import { LecPostService } from '../lec-post.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { DeletelecCommentComponent } from '../deletelec-comment/deletelec-comment.component';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  id!: number;
  currentUser: any;
  post: Post = new Post;
  comment: Comment[] = [];
  constructor(
    private route: ActivatedRoute,
    private postLecService: LecPostService,
    private router: Router,
    private storageService: StorageService,
    public dialog: MatDialog,) {

  }

  public dataSource !: MatTableDataSource<any>;
  // public post !: Post[];
  displayedColumns: string[] = ['id', 'lecture', 'post_content', 'description', 'created_at', 'action'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  ngOnInit(): void {
    this.getAllComment();
    this.id = this.route.snapshot.params['id'];

    this.post = new Post();
    this.postLecService.getPostByID(this.id).subscribe(data => {
      this.post = data
    })

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getAllComment() {
    this.id = this.route.snapshot.params['id'];
    this.postLecService.getAllComment(this.id)
      .subscribe(data => {
        this.comment = data;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        console.log(('Comment: '), data);
      })
  }

  goToPost() {
    this.currentUser = this.storageService.getUser();
    this.router.navigate(['/lecturer/' + this.currentUser.userAccountId + '/post']);
  }

  openDeleteComment(idU:number, idC:number){
    this.dialog.open(DeletelecCommentComponent, {
      data: { userAccountId: idU, commentId: idC  }
    }).afterClosed().subscribe( () => this.getAllComment())
  }

}
