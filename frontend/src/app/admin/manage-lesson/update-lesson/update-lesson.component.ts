import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-update-lesson',
  templateUrl: './update-lesson.component.html',
  styleUrls: ['./update-lesson.component.css']
})
export class UpdateLessonComponent implements OnInit {
  id!:number;
  lesson: Lesson = new Lesson();
  status!: boolean;
  constructor(
    private lessonService: LessonService,
     private route: ActivatedRoute, 
     private router: Router,
     private nofiService: NotificationService){

  }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.lessonService.getLessonByID(this.id).subscribe(data =>{
      this.lesson = data;
    },error => console.log(error));


    this.lessonService.getStatus().subscribe(res =>{
      this.status = res.status;
    })
  }
  onSubmit(){
    this.lessonService.updateLesson(this.id, this.lesson).subscribe(data =>{
      this.goTolessonList();
      this.nofiService.openSnackBar('Update lesson successful','OK');
    },
    error => console.log(error));
  }
  goTolessonList(){
    this.router.navigate(['admin/lesson']);
  }

}
