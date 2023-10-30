import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-lesson-detail',
  templateUrl: './lesson-detail.component.html',
  styleUrls: ['./lesson-detail.component.css']
})
export class LessonDetailComponent implements OnInit{
  id!:number;
  lesson:Lesson = new Lesson;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: number,
    private route: ActivatedRoute, 
    private lessonService: LessonService, 
    private router:Router,
    public dialogRef: MatDialogRef<LessonDetailComponent>,
    ){}
  ngOnInit(): void {
    // this.id = this.route.snapshot.params['id'];

    this.lesson = new Lesson();
    this.lessonService.getLessonByID(this.data).subscribe(rs =>{
      this.lesson = rs
    });

  }

  // backtoList(){
  //   this.router.navigate(['admin/book/'+this.id+'/lesson']);
  // }
  gotoKanji(){
    this.router.navigate(['admin/list-kanji']);
  }
  gotoWriting(){
    this.router.navigate(['admin/lesson/'+this.data+'/writing']);
  }
  gotoListening(){
    this.router.navigate(['admin/lesson/'+this.data+'/listening']);
  }
  gotoReading(){
    this.router.navigate(['admin/lesson/'+this.data+'/reading']);
  }
  gotoKaiwa(){
    this.router.navigate(['admin/lesson/'+this.data+'/kaiwa']);
  }
}
