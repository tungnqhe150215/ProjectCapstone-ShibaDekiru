import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';

import { MatDialog } from '@angular/material/dialog';
import { Reading } from 'src/app/core/models/reading';
import { Lesson } from 'src/app/core/models/lesson';
import { ReadingQuestion } from 'src/app/core/models/reading-question';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'app-list-reading',
  templateUrl: './list-reading.component.html',
  styleUrls: ['./list-reading.component.css']
})
export class ListReadingComponent implements OnInit{

  public dataSource !: MatTableDataSource<Reading>;

  p: number = 1;
  idB!:number;
  lessonN: Lesson[] = [];
  displayedColumns: string[] = ['id', 'name','image', 'lesson'];
  id!:number
  readingQuestion: ReadingQuestion[] =[];
  // @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort1!: MatSort;

  reading: Reading[] = [];
  lesson: Lesson = new Lesson;
  Lbook: Book = new Book;
  
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getReadingbyLessonID();
    this.getLessonById();
    this.getLessonByBookID();
    this.getBookById();
  }

  getReadingbyLessonID(){
    this.id = this.route.snapshot.params['idL'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.reading = [];
    this.studentLessonService.getReadingByLesson(this.id)
    .subscribe({
      next:data =>{
        this.reading = data;
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

  idR!:number;
  getQuestionInReading(idR:number){
    this.readingQuestion = [];
    this.studentLessonService.getReadingQuesByReading(idR)
    .subscribe({
      next:data =>{
        this.readingQuestion = data;
        // this.dataSource.paginator = this.paginator;
        console.log(data)
      }
    })
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

      //pagging
      key: string = 'id';
      reverse: boolean = false;
      sort(key: string) {
        this.key = key;
        this.reverse = !this.reverse;
      }
}
