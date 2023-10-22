import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Post } from 'src/app/core/models/post';
import { PostService } from 'src/app/admin/manage-post/post.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import { CreatePostComponent } from '../create-post/create-post.component';

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

  constructor(
    private postService: PostService, 
    private router: Router,
    public dialog: MatDialog,
    private nofiService: NotificationService,
    ) {

  }

  ngOnInit(): void {
    this.GetPost();


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


  addData(){
    this.dialog.open(CreatePostComponent,{

    }).afterClosed().subscribe( () => this.GetPost())
  }

  postDetail(id: number) {
    this.router.navigate(['/lecturers/post/post-detail', id]);
  }

  updatePost(id:number){
    this.router.navigate(['/lecturers/post/update-post',id]);
  }
  deletePost(id: number){
    this.postService.deletePost(id).subscribe( {
      next:() =>{
        this.nofiService.openSnackBar('Post deleted !', 'Cancel');
        this.GetPost();
      },
      error: console.log,
    })
  }
}
