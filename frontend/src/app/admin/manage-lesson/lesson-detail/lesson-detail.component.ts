import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';

@Component({
  selector: 'app-lesson-detail',
  templateUrl: './lesson-detail.component.html',
  styleUrls: ['./lesson-detail.component.css']
})
export class LessonDetailComponent implements OnInit{
  id!:number;
  lesson:Lesson = new Lesson;

  constructor(private route: ActivatedRoute, private lessonService: LessonService, private router:Router,){

  }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.lesson = new Lesson();
    this.lessonService.getLessonByID(this.id).subscribe(data =>{
      this.lesson = data
    });

  }

  backtoList(){
    this.router.navigate(['admin/lesson']);
  }
  gotoKanji(){
    this.router.navigate(['admin/list-kanji']);
  }
  gotoWriting(){
    this.router.navigate(['admin/lesson/'+this.id+'/writing']);
  }
  gotoListening(){
    this.router.navigate(['admin/lesson/'+this.id+'/listening']);
  }
  gotoReading(){
    this.router.navigate(['admin/lesson/'+this.id+'/reading']);
  }
  gotoKaiwa(){
    this.router.navigate(['admin/lesson/'+this.id+'/kaiwa']);
  }
}
