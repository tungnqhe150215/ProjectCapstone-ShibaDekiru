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
@Component({
  selector: 'app-list-kanjiless',
  templateUrl: './list-kanjiless.component.html',
  styleUrls: ['./list-kanjiless.component.css']
})
export class ListKanjilessComponent implements OnInit{

  public dataSource !: MatTableDataSource<Kanji>;

  displayedColumns: string[] = ['id', 'name','chineseMean', 'lesson' ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  lesson: Lesson[] =[];
  kanji: Kanji[] =[];
  
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){}


  ngOnInit(): void {
   this.getKanjiByLesson();
   this.getLessonByBookID();
  }

  getKanjiByLesson(){
    const idLesson = this.studentLessonService.getLessonID();
    this.kanji = [];
    this.studentLessonService.getKanjiByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.kanji = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }

  id !: number;
  getKanjiByLessons(id:number){
    this.id = this.route.snapshot.params['id'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.kanji = [];
    this.studentLessonService.getKanjiByLesson(id)
    .subscribe({
      next:(res) =>{
        this.kanji = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }

  getLessonByBookID(){
    // this.studentLessonService.setBookId(this.id);
    // this.id = this.route.snapshot.params['id'];
    const  idBook = this.studentLessonService.getBookId();
    this.lesson = [];
    this.studentLessonService.getLessonByBook(idBook).subscribe({
      next:(res) =>{
        this.lesson = res;
        // this.dataSource = new MatTableDataSource(res);
        // this.dataSource.paginator = this.paginator;
        console.log(res)
      },
    })

  }


  kanjiDetail(id: number){
    this.dialog.open(KanjiDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getKanjiByLesson());
  }

}
