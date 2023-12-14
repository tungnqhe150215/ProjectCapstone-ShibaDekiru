import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import { Kanji } from 'src/app/core/models/kanji';
import { MatDialog } from '@angular/material/dialog';
import { KanjiDetailComponent } from '../../list-knowledge/kanji/kanji-detail/kanji-detail.component';
import { Lesson } from 'src/app/core/models/lesson';
import { Book } from 'src/app/core/models/book';
@Component({
  selector: 'app-list-kanjiless',
  templateUrl: './list-kanjiless.component.html',
  styleUrls: ['./list-kanjiless.component.css']
})
export class ListKanjilessComponent implements OnInit{

  public dataSource !: MatTableDataSource<Kanji>;

  displayedColumns: string[] = ['id', 'name','chineseMean'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  lesson: Lesson[] =[];
  kanji: Kanji[] =[];
  idB!:number;
  id !: number;
  Lbook: Book = new Book;
  lessonN: Lesson = new Lesson;
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){}


  ngOnInit(): void {
   this.getKanjiByLesson();
   this.getLessonByBookID();
   this.getLessonById();
   this.getBookById();
  }

  getKanjiByLesson(){
    this.id = this.route.snapshot.params['idL'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.kanji = [];
    this.studentLessonService.getKanjiByLesson(this.id)
    .subscribe({
      next:(res) =>{
        this.kanji = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }

  LessonDetail(id:number, idL:number) {
    this.id = idL;
    this.studentLessonService.setLessonID(idL);
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/detail']);
    // this.getLessonById();
  }

  getLessonById() {
    this.lessonN = new Lesson();
    this.id = this.route.snapshot.params['idL'];
    this.studentLessonService.getLessonById(this.id).subscribe(res => {
      this.lessonN = res
    })
  }
  
  // getKanjiByLessons(id:number){
  //   this.id = this.route.snapshot.params['id'];
  //   const idLesson = this.studentLessonService.getLessonID();
  //   this.kanji = [];
  //   this.studentLessonService.getKanjiByLesson(id)
  //   .subscribe({
  //     next:(res) =>{
  //       this.kanji = res;
  //       this.dataSource = new MatTableDataSource(res);
  //       this.dataSource.paginator = this.paginator;
  //       console.log(res)
  //     }
  //   })
  // }

  getLessonByBookID(){
    // this.studentLessonService.setBookId(this.id);
    this.idB = this.route.snapshot.params['id'];
    // const  idBook = this.studentLessonService.getBookId();
    this.lesson = [];
    this.studentLessonService.getLessonByBook(this.idB).subscribe({
      next:(res) =>{
        this.lesson = res;
        // this.dataSource = new MatTableDataSource(res);
        // this.dataSource.paginator = this.paginator;
        console.log(res)
      },
    })

  }

  getBookById()  {
    this.idB = this.route.snapshot.params['id'];
    this.Lbook = new Book();
    // const idBook = this.studentLessonService.getBookId();
    this.studentLessonService.getBookById(this.idB).subscribe(res =>{
    this.Lbook = res
   })
  }

  kanjiDetail(id: number){
    this.dialog.open(KanjiDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getKanjiByLesson());
  }

}
