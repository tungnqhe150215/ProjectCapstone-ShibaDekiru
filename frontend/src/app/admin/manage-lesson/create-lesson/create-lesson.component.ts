import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";

@Component({
  selector: 'app-create-lesson',
  templateUrl: './create-lesson.component.html',
  styleUrls: ['./create-lesson.component.css'],

})
export class CreateLessonComponent implements OnInit {

  lesson: Lesson = new Lesson();
  file!: File ;
  drive: Drive = new Drive();

  constructor(
    public dialogRef: MatDialogRef<CreateLessonComponent>,
    private lessonService: LessonService,
    private fileService:FileService,
    private router:Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService,

    ){}
  ngOnInit(): void {
    // this.empForm.patchValue(this.data);
  }
//     createLesson(){
//       this.lessonService.createLesson(this.data, this.lesson).subscribe(data =>{
//         console.log(data);
//         this.nofiService.openSnackBar('Tạo bài học thành công');
//         this.dialogRef.close();
//       },
//       )
// }

createLesson() {

  this.fileService.uploadFile(this.file).subscribe(data => {
    console.log(data)
    this.drive = data as Drive
    this.lesson.image = this.drive.fileId

    this.lessonService.createLesson(this.data, this.lesson).subscribe({
      next: (data) => {
        console.log(data);
        this.nofiService.openSnackBar('Tạo bài học thành công');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Có lỗi xảy ra khi thêm bài học vui lòng kiểm tra lại');
      },
    });
  })

}


    gotoLessonList(){
      this.router.navigate(['/admin/lesson']);
    }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if(element != null) {
      element.value = this.file.name;
    }
  }

}
