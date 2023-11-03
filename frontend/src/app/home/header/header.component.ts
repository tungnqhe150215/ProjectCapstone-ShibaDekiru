import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { StudentLessonService } from '../lesson/student-lesson.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'home-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css','../home-style.css']
})
export class HeaderComponent implements AfterViewInit,OnInit{

  @ViewChild('header') elementView!: ElementRef<HTMLInputElement>;

  contentHeight !: number;
  scrollNumber !:number;

  ngAfterViewInit(): void {

  }

  book: Book[]= [];
  constructor(
    private studentLessonService: StudentLessonService,
    private router: Router,
    private route:ActivatedRoute,
    ) {}

    
  ngOnInit(): void {
    this.getAllBook();
  }

  getAllBook(){
   this.studentLessonService.getAllBook()
   .subscribe( data =>{
    this.book = data;
    console.log(data);

   })  
  }

  LessonByBook(id:number){
    this.studentLessonService.setBookId(id);
    this.router.navigate(['book/'+id+'/lesson']);
  }
}
