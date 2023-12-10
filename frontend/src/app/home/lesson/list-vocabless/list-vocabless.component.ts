import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { StudentLessonService } from '../student-lesson.service';
import { MatDialog } from '@angular/material/dialog';
import { VocabDetailComponent } from '../../list-knowledge/vocab/vocab-detail/vocab-detail.component';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-list-vocabless',
  templateUrl: './list-vocabless.component.html',
  styleUrls: ['./list-vocabless.component.css']
})
export class ListVocablessComponent implements OnInit{

  public dataSource !: MatTableDataSource<Vocabulary>;

  displayedColumns: string[] = ['id', 'name','meaning', 'lesson'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  lesson: Lesson[] =[];
  vocab: Vocabulary[] =[];
  id !: number;
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){}

  
  ngOnInit(): void {
    this.getVocabByLessonID();
    this.getLessonByBookID();
  }

  getVocabByLessonID(){
    // this.id = this.route.snapshot.params['id'];
    const idLesson = this.studentLessonService.getLessonID();
    this.vocab = [];
    this.studentLessonService.getVocabByLesson(idLesson)
    .subscribe({
      next:(res) =>{
        this.vocab = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }

  getVocabByLessonIDs(id:number){
    this.id = this.route.snapshot.params['id'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.vocab = [];
    this.studentLessonService.getVocabByLesson(id)
    .subscribe({
      next:(res) =>{
        this.vocab = res;
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


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  vocabDetail(id: number){
    this.dialog.open(VocabDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getVocabByLessonID());
  }

}
