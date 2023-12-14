import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatDialog } from '@angular/material/dialog';
import { Kaiwa } from 'src/app/core/models/kaiwa';
import { Lesson } from 'src/app/core/models/lesson';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'app-list-kaiwa',
  templateUrl: './list-kaiwa.component.html',
  styleUrls: ['./list-kaiwa.component.css']
})
export class ListKaiwaComponent implements OnInit{

  panelOpenState = false;
  
  public dataSource !: MatTableDataSource<Kaiwa>;

  displayedColumns: string[] = ['id', 'name', 'lesson','script' ];

  // @ViewChild(MatPaginator) paginator!: MatPaginator;
  // @ViewChild(MatSort) sort!: MatSort;

  p: number = 1;
  idB!:number;
  lessonN: Lesson[] = [];
  Lbook: Book = new Book;
  kaiwa: Kaiwa[]=[];
  lesson: Lesson = new Lesson;
  slicedItems: string[][] = []; // Khai báo ở đây
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route:ActivatedRoute,
    public dialog: MatDialog,
  ){}


  ngOnInit(): void {
   this.getKaiwaByLessonID();
   this.getLessonById();
   this.getLessonByBookID();
   this.getBookById();
  }

  id!:number
  getKaiwaByLessonID(){
    this.id = this.route.snapshot.params['idL'];
    // this.kaiwa = [];
    this.studentLessonService.getKaiwaByLesson(this.id)
    .subscribe({
      next:data =>{
        this.kaiwa = data;
        this.dataSource = new MatTableDataSource(data);
        // this.dataSource.paginator = this.paginator;
        this.slicedItems = this.getSlicedItems(this.kaiwa, 'script');
        console.log(data)
      }
    })
  }

  getSlicedItems(data: any[], propertyName: string): string[][] {
    return data.map(item => item[propertyName]
      .split('。')
      .filter((subItem: string) => subItem.trim() !== '')
    );
  }


  getLessonById() {
    this.lesson = new Lesson();
    this.id = this.route.snapshot.params['idL'];
    this.studentLessonService.getLessonById(this.id).subscribe(data => {
      this.lesson = data
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
