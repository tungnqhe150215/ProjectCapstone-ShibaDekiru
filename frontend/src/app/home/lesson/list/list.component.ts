import { Component, OnInit, ViewChild } from '@angular/core';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { Router, ActivatedRoute } from '@angular/router';
import { StudentLessonService } from '../student-lesson.service';
import { Lesson } from 'src/app/core/models/lesson';
import { Book } from 'src/app/core/models/book';


@Component({
  selector: 'app-lesson-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {

  // id!: number;
  lesson: Lesson[] =[];
  book: Book[]= [];
  // book: Book = new Book;
  constructor(
   
    private studentLessonService: StudentLessonService,
    private router: Router,
    private route:ActivatedRoute,
  ) {}
  // displayedColumns: string[] = ['Stt', 'Name'];
  // dataSource = new MatTableDataSource<any>;

  // @ViewChild(MatPaginator) paginator!: MatPaginator;

  
  ngOnInit(): void {
    // this.route.queryParams.subscribe(params => {
    //   const bookId = params['bookId'];
    //   // Lưu bookId trong service hoặc biến của component để sử dụng sau này
    // });
    this.getLessonByBookID();
    // this.getAllBook();
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
  

  // applyFilter(event: Event) {
  //   const filterValue = (event.target as HTMLInputElement).value;
  //   this.dataSource.filter = filterValue.trim().toLowerCase();

  //   if (this.dataSource.paginator) {
  //     this.dataSource.paginator.firstPage();
  //   }
  // }
  
  LessonDetail(id:number){
    this.studentLessonService.setLessonID(id);
    this.router.navigate(['./lesson/'+id+'/detail']);
  
  }
  // LessonDetail(id:number){
  //   this.router.navigate(['./lesson/'+id+'/detail']);
  
  // }

  // lessons = [
  //   {id: 1, name: 'bai 1'},
  //   {id: 2, name: 'bai 2'},
  //   {id: 3, name: 'bai 3'},
  //   {id: 4, name: 'bai 4'},
  //   {id: 5, name: 'bai 5'},
  //   {id: 6, name: 'bai 6'},
  //   {id: 7, name: 'bai 7'},
  //   {id: 8, name: 'bai 8'},
  //   {id: 9, name: 'bai 9'},
  // ]

 
}
