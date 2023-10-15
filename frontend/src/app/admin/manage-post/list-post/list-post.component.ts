import { Component, OnInit, ViewChild } from '@angular/core';
import { Post } from 'src/app/core/models/post';
import { PostService } from '../post.service';
import { Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';


@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit{
  public dataSource !: MatTableDataSource<Post>;
  // public post !: Post[];
  displayedColumns: string[] = ['id', 'lecture', 'post_content', 'description','created_at','status', 'action'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  post: Post[] = [];

  constructor(private postService: PostService, private router: Router){
    
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

  private GetPost(){
    this.postService.getPostlist()
    .subscribe( data =>{
      this.post = data;
      this.dataSource = new MatTableDataSource(this.post);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    }
    )
   console.log(this.post)   
    

    // this.postService.getPostlist().subscribe(data=>{
    //   this.post = data;
    // });
  }

  postDetail(id:number){
    this.router.navigate(['post-detail',id]);
  }
}
