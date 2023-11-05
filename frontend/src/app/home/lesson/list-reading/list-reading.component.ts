import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';

import { MatDialog } from '@angular/material/dialog';
import { Reading } from 'src/app/core/models/reading';

@Component({
  selector: 'app-list-reading',
  templateUrl: './list-reading.component.html',
  styleUrls: ['./list-reading.component.css']
})
export class ListReadingComponent implements OnInit{

  public dataSource !: MatTableDataSource<Reading>;

  displayedColumns: string[] = ['id', 'name','image', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  reading: Reading[] = [];

  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getReadingbyLessonID();
  }

  getReadingbyLessonID(){
    const idLesson = this.studentLessonService.getLessonID();
    this.reading = [];
    this.studentLessonService.getReadingByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.reading = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })

  }
}
