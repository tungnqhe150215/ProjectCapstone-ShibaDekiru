import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Post } from 'src/app/core/models/post';
import { PostService } from 'src/app/admin/manage-post/post.service';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import { CreatePostComponent } from '../create-post/create-post.component';
import { LecPostService } from '../lec-post.service';
import { UserService } from 'src/app/admin/manage-user/user.service';
import { UserAccount } from 'src/app/core/models/user-account';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {
  public dataSource !: MatTableDataSource<Post>;
  // public post !: Post[];
  displayedColumns: string[] = ['id', 'lecture', 'post_content', 'description', 'created_at', 'status', 'action'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  post: Post[] = [];
  user: UserAccount = new UserAccount;
  idU = 3;
  constructor(
    private postService: PostService,
    private lecpostService: LecPostService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private nofiService: NotificationService,
  ) {

  }


  getPostbyUser() {
    // this.id = this.route.snapshot.params['id'];
    this.user = new UserAccount;
    this.userService.getUserByID(this.idU).subscribe(data => {
      this.user = data;
      console.log(data);
    });
    this.post = []
    this.lecpostService.getlistByUser(this.idU).subscribe(data => {
      this.post = data;
      this.dataSource = new MatTableDataSource(this.post);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.post)
    console.log(this.idU)

  }

  ngOnInit(): void {
    this.GetPost();
    // this.getPostbyUser();

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }




  private GetPost() {
    this.postService.getPostlist()
      .subscribe(data => {
        this.post = data;
        this.dataSource = new MatTableDataSource(this.post);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        console.log(data)
      }
      )
    console.log(this.post)


  }


  addData() {
    this.dialog.open(CreatePostComponent, {

    }).afterClosed().subscribe(() => this.GetPost())
  }

  postDetail(id: number) {
    this.router.navigate(['/lecturer/post/post-detail', id]);
  }

  updatePost(id: number) {
    this.router.navigate(['/lecturer/post/update-post', id]);
  }
  deletePost(id: number) {
    this.postService.deletePost(id).subscribe({
      next: () => {
        this.nofiService.openSnackBar('Post deleted !', 'Cancel');
        this.GetPost();
      },
      error: console.log,
    })
  }
}
