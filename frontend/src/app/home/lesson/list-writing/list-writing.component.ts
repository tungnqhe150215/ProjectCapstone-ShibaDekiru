import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';

import { MatDialog } from '@angular/material/dialog';
import { Writing } from 'src/app/core/models/writing';

@Component({
  selector: 'app-list-writing',
  templateUrl: './list-writing.component.html',
  styleUrls: ['./list-writing.component.css']
})
export class ListWritingComponent implements OnInit{

  public dataSource !: MatTableDataSource<Writing>;

  displayedColumns: string[] = ['id', 'name', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  writing: Writing[] = [];

  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getWritingByLessonID();
  }

  getWritingByLessonID(){
    const idLesson = this.studentLessonService.getLessonID();
    this.writing = [];
    this.studentLessonService.getWritingByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.writing = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }
}
