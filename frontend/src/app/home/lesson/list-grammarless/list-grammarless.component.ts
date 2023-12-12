import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import { Grammar } from 'src/app/core/models/grammar';
import { MatDialog } from '@angular/material/dialog';
import { GrammarDetailComponent } from '../../list-knowledge/grammar/grammar-detail/grammar-detail.component';
import { Lesson } from 'src/app/core/models/lesson';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'app-list-grammarless',
  templateUrl: './list-grammarless.component.html',
  styleUrls: ['./list-grammarless.component.css']
})
export class ListGrammarlessComponent implements OnInit {

  public dataSource !: MatTableDataSource<Grammar>;

  displayedColumns: string[] = ['id', 'name','structure','explain'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  lesson: Lesson[] =[];
  grammar: Grammar[]=[];
  id !: number;
  idB!:number;
  lessonN: Lesson = new Lesson;
  Lbook: Book = new Book;
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){}

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  
  ngOnInit(): void {
    this.getGrammarByLesson();
    this.getLessonById();
    this.getBookById();
    this.getLessonById();
    this.getLessonByBookID();
  }

  getGrammarByLesson(){
    this.id = this.route.snapshot.params['idL'];
    const idLesson = this.studentLessonService.getLessonID();
    this.grammar = [];
    this.studentLessonService.getGrammarByLesson(this.id)
    .subscribe({
      next:(res) =>{
        this.grammar = res;
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        console.log(res)
      }
    })
  }
  grammarDetail(id: number){
    this.dialog.open(GrammarDetailComponent ,{
      data:id
    }).afterClosed().subscribe( () => this.getGrammarByLesson());
  }
  
  getLessonById() {
    this.lessonN = new Lesson();
    this.id = this.route.snapshot.params['idL'];
    this.studentLessonService.getLessonById(this.id).subscribe(res => {
      this.lessonN = res
    })
  }

  LessonDetail(id:number, idL:number) {
    this.id = idL;
    this.studentLessonService.setLessonID(idL);
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/detail']);
  }

  getBookById()  {
    this.idB = this.route.snapshot.params['id'];
    this.Lbook = new Book();
    this.studentLessonService.getBookById(this.idB).subscribe(res =>{
    this.Lbook = res
   })
  }

  getLessonByBookID(){
    this.idB = this.route.snapshot.params['id'];
    this.lesson = [];
    this.studentLessonService.getLessonByBook(this.idB).subscribe({
      next:(res) =>{
        this.lesson = res;
        console.log(res)
      },
    })

  }
}
