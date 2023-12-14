import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';

import { MatDialog } from '@angular/material/dialog';
import { Writing } from 'src/app/core/models/writing';
import { Lesson } from 'src/app/core/models/lesson';
import { WritingQuestion } from 'src/app/core/models/writing-question';

@Component({
  selector: 'app-list-writing',
  templateUrl: './list-writing.component.html',
  styleUrls: ['./list-writing.component.css']
})
export class ListWritingComponent implements OnInit{

  public dataSource !: MatTableDataSource<Writing>;

  displayedColumns: string[] = ['id', 'name', 'lesson'];

  // @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort!: MatSort;
  
  lesson: Lesson = new Lesson;
  writingQuestion :WritingQuestion[] =[];
  writing: Writing[] = [];
  id !: number;
  p: number = 1;
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getWritingByLessonID();
    this.getLessonById();
    // this.getQuestionWritingByLesson();
  }

  getWritingByLessonID(){
    this.id = this.route.snapshot.params['idL'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.writing = [];
    this.studentLessonService.getWritingByLesson(this.id)
    .subscribe({
      next:data =>{
        this.writing = data;
        this.dataSource = new MatTableDataSource(data);
        // this.dataSource.paginator = this.paginator;
        console.log(data)
      }
    })
  }
  
  getLessonById() {
    this.lesson = new Lesson();
    this.id = this.route.snapshot.params['idL'];
    this.studentLessonService.getLessonById(this.id).subscribe(data => {
      this.lesson = data
    })
  }

  idW!:number;
  getQuestionWritingByLesson(idW:number){
    // this.id = this.route.snapshot.params['idL'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.writingQuestion = [];
    this.studentLessonService.getWritingQuesByWriting(idW)
    .subscribe({
      next:data =>{
        this.writingQuestion = data;
        // this.dataSource.paginator = this.paginator;
        console.log(data)
      }
    })
  }
    key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
