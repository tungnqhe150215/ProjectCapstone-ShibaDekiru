import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';

import { MatDialog } from '@angular/material/dialog';
import { Kaiwa } from 'src/app/core/models/kaiwa';

@Component({
  selector: 'app-list-kaiwa',
  templateUrl: './list-kaiwa.component.html',
  styleUrls: ['./list-kaiwa.component.css']
})
export class ListKaiwaComponent implements OnInit{

  public dataSource !: MatTableDataSource<Kaiwa>;

  displayedColumns: string[] = ['id', 'name', 'lesson' ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  kaiwa: Kaiwa[]=[];
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){}


  ngOnInit(): void {
   this.getKaiwaByLessonID();
  }

  getKaiwaByLessonID(){
    const idLesson = this.studentLessonService.getLessonID();
    this.kaiwa = [];
    this.studentLessonService.getKaiwaByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.kaiwa = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }
}
