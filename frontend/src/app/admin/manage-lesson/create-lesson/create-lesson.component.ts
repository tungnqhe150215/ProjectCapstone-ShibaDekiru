import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-create-lesson',
  templateUrl: './create-lesson.component.html',
  styleUrls: ['./create-lesson.component.css'],

})
export class CreateLessonComponent implements OnInit {

  lesson: Lesson = new Lesson();
  constructor(
    public dialogRef: MatDialogRef<CreateLessonComponent>,
    private lessonService: LessonService,
    private router:Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService,
    ){}
  ngOnInit(): void {
    // this.empForm.patchValue(this.data);
  }
    createLesson(){
      this.lessonService.createLesson(this.lesson).subscribe(data =>{
        console.log(data);
        this.nofiService.openSnackBar('Create lesson successful','OK');
        this.dialogRef.close();
      })
    }

    gotoLessonList(){
      this.router.navigate(['/admin/lesson']);
    }
}
