import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Writing} from "../../../core/models/writing";
import {AdminManageWritingService} from "../admin-manage-writing.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-list-writing',
  templateUrl: './list-writing.component.html',
  styleUrls: ['./list-writing.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule],
})
export class ListWritingComponent implements OnInit{
  displayedColumns: string[] = ['id', 'topic', 'lesson-name','action'];
  dataSource!: MatTableDataSource<Writing> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  writing :Writing[] = [];
  id!: number;

  constructor(private manageWritingService:AdminManageWritingService, private router:Router, private route:ActivatedRoute) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getWriting();

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getWriting(){
    this.id = this.route.snapshot.params['id'];
    this.writing = []
    this.manageWritingService.getWritingByLesson(this.id).subscribe(data => {
      this.writing = data;
      this.dataSource = new MatTableDataSource(this.writing);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.writing)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  deleteWriting(id:number){
    this.manageWritingService.deleteWriting(id).subscribe(data => {
      this.getWriting();
    })
  }

  getWritingDetail(id:number){
    this.router.navigate(['writing',id]);
  }
}
