import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
  constructor(private route: ActivatedRoute, private lessonService: LessonService){

  }
  ngOnInit(): void {
    
  }


}
