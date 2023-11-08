import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatDialog } from '@angular/material/dialog';
import { Listening } from 'src/app/core/models/listening';

@Component({
  selector: 'app-list-listening',
  templateUrl: './list-listening.component.html',
  styleUrls: ['./list-listening.component.css']
})
export class ListListeningComponent implements OnInit {

  public dataSource !: MatTableDataSource<Listening>;

  displayedColumns: string[] = ['id', 'name', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  listening: Listening[] = [];

  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getListenByLessonID();
  }

  getListenByLessonID() {
    const idLesson = this.studentLessonService.getLessonID();
    this.listening = [];
    this.studentLessonService.getListeningByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.listening = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })

  }
}
