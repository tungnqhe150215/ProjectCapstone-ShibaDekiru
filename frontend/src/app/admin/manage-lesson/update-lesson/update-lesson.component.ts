import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import {Drive} from "../../../core/models/drive";
import {FileService} from "../../../shared/services/file.service";
import {FilePreviewService} from "../../../shared/services/file-preview.service";

@Component({
  selector: 'app-update-lesson',
  templateUrl: './update-lesson.component.html',
  styleUrls: ['./update-lesson.component.css']
})
export class UpdateLessonComponent implements OnInit {
  id!:number;
  lesson: Lesson = new Lesson();
  file!: File ;
  drive: Drive = new Drive();
  status!: boolean;
  constructor(
    public dialogRef: MatDialogRef<UpdateLessonComponent>,
    private lessonService: LessonService,
    private fileService:FileService,
    private filePreviewService: FilePreviewService,
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
    if (this.file == null || this.file.size == 0) {
      this.lesson.image = ""
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
    } else {

      this.fileService.uploadFile(this.file).subscribe(data => {
        console.log(data)
        this.drive = data as Drive
        this.lesson.image = this.drive.fileId
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
      })

    }

  }
  goTolessonList(){
    this.router.navigate(['admin/lesson']);
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
    var element = document.getElementById("fakeFileInput") as HTMLInputElement | null;
    if (element != null) {
      element.value = this.file.name;
    }
    if (this.file) {
      this.previewFile(this.file);
    }
  }

  previewFile(file: File) {
    const reader = new FileReader();

    reader.onload = () => {
      const preview = reader.result as string;
      this.filePreviewService.changePreview(preview);
    };

    // Read the file as a data URL
    reader.readAsDataURL(file);
  }
}
