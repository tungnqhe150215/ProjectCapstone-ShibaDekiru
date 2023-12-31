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
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import { DeletePostComponent } from '../delete-post/delete-post.component';

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
  // idU:number = 1;
  constructor(
    private lecpostService: LecPostService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private nofiService: NotificationService,
    private storageService: StorageService,
  ) {

  }



   currentUser: any;
   firstName!:string;
   lastName!:string;
   nickName!:string;
  ngOnInit(): void {
    // this.GetPost();

    this.getPostbyUser();

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  getPostbyUser() {
    this.currentUser = this.storageService.getUser();
    this.nickName = this.currentUser.nickName;
    this.lecpostService.getlistByUser(this.currentUser.userAccountId).subscribe({
      next:(res) =>{
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: console.log,
    })
  }

  // private GetPost() {
  //   this.postService.getPostlist()
  //     .subscribe(data => {
  //       this.post = data;
  //       this.dataSource = new MatTableDataSource(this.post);
  //       this.dataSource.paginator = this.paginator;
  //       this.dataSource.sort = this.sort;
  //       console.log(data)
  //     }
  //     )
  //   console.log(this.post)


  // }

  // openDeletePostDialog(id:number){
  //   this.dialog.open(DeletePostComponent,{
  //     data:id
  //   }).afterClosed().subscribe(() => this.getPostbyUser())
  // }

  //not loading when delete
  openDeletePostDialog(id:number){
    this.dialog.open(DeletePostComponent,{
      data:id
    }).afterClosed().subscribe( () => {
      this.getPostbyUser();
    } )
  }

  addData() {
    this.dialog.open(CreatePostComponent, {

    }).afterClosed().subscribe(() => this.getPostbyUser())
  }

  postDetail(id: number) {
    this.router.navigate(['/lecturer/post/post-detail', id]);
  }


  updatePost(id: number) {
    this.router.navigate(['/lecturer/post/update-post', id]);
  }
  // deletePost(id: number) {
  //   this.lecpostService.deletePost(id).subscribe({
  //     next: () => {
  //       this.nofiService.openSnackBar('Đã xóa bài viết');
  //       this.getPostbyUser();
  //     },
  //     error: () =>{
  //       this.nofiService.openSnackBar('Có lỗi xảy ra khi xóa bài viết');
  //     }
  //   })
  // }
}
