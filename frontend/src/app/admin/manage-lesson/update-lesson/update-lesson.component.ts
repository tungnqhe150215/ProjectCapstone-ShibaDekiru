import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
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
    public dialogRef: MatDialogRef<UpdateLessonComponent>,
    private lessonService: LessonService,
     private route: ActivatedRoute, 
     private router: Router,
     private nofiService: NotificationService,
     @Inject(MAT_DIALOG_DATA) public data: number,
     ){

  }
  ngOnInit(): void {
    // this.id = this.route.snapshot.params['id'];
    this.lesson = new Lesson
    this.lessonService.getLessonByID(this.data).subscribe(data =>{
      this.lesson = data;
    },error => console.log(error));


    // this.lessonService.getStatus().subscribe(res =>{
    //   this.status = res.status;
    // })
  }
  // onSubmit(){
  //   this.lessonService.updateLesson(this.data, this.lesson).subscribe(data =>{
  //     // this.goTolessonList();
  //     this.nofiService.openSnackBar('Update lesson successful','OK');
  //     this.dialogRef.close();
  //   },
  //   error => console.log(error));
  // }

  onSubmit() {
    this.lessonService.updateLesson(this.data, this.lesson).subscribe({
      next: (data) => {
        // this.goTolessonList();
        this.nofiService.openSnackBar('Cập nhật bài học thành công');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Cập nhật bài học thất bại vui lòng kiểm tra lại!');
      },
    });
  }
  goTolessonList(){
    this.router.navigate(['admin/lesson']);
  }

}
