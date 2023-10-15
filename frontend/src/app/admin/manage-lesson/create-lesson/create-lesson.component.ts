import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';

@Component({
  selector: 'app-create-lesson',
  templateUrl: './create-lesson.component.html',
  styleUrls: ['./create-lesson.component.css']
})
export class CreateLessonComponent implements OnInit {
  
 


  lesson: Lesson = new Lesson();
  constructor(private lessonService: LessonService,private router:Router,){}
  ngOnInit(): void {
    // this.empForm.patchValue(this.data);
  }
  saveLesson(){
    this.lessonService.createLesson(this.lesson).subscribe(data =>{
      console.log(data);
      this.goToLessonList();
    },error =>console.log(error));

  }
  
  goToLessonList(){
      this.router.navigate(['lesson']);
  }

  onSubmit(){
    console.log(this.lesson);
    this.saveLesson();
  }


  
}
