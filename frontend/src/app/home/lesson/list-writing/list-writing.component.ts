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
import { Book } from 'src/app/core/models/book';

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
  idB!:number;
  lessonN: Lesson[] = [];
  Lbook: Book = new Book;
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
    this.getLessonByBookID();
    this.getBookById();
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

  getLessonByBookID() {
    this.idB = this.route.snapshot.params['id'];
    const idBook = this.studentLessonService.getBookId();
    this.lessonN = [];
    this.studentLessonService.getLessonByBook(this.idB).subscribe({
      next: (res) => {
        this.lessonN = res;
        console.log(res)
      },
    })
  }

  LessonDetail(id:number, idL:number) {
    this.id = idL;
    this.studentLessonService.setLessonID(idL);
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/detail']);
    this.getLessonById();
  }

  getBookById()  {
    this.idB = this.route.snapshot.params['id'];
    this.Lbook = new Book();
    this.studentLessonService.getBookById(this.idB).subscribe(res =>{
    this.Lbook = res
   })
  }

}
