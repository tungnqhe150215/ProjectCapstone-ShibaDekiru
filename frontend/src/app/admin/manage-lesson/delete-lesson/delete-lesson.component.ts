import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { LessonService } from 'src/app/core/services/lesson.service';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ManageBookService } from '../../manage-book/manage-book.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Book } from 'src/app/core/models/book';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-delete-lesson',
  templateUrl: './delete-lesson.component.html',
  styleUrls: ['./delete-lesson.component.css']
})
export class DeleteLessonComponent implements OnInit{


  ngOnInit(): void {
    // this.getLesson();
    
  }
  constructor(
    private bookService: ManageBookService,
    private lessonService: LessonService, 
    private nofiService: NotificationService,
    private router:Router, 
    private route:ActivatedRoute,
    public dialogRef: MatDialogRef<DeleteLessonComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}

  deleteLesson(id: number) {
    this.lessonService.deleteLesson(id).subscribe({
      next: (res) => {
        this.nofiService.openSnackBar('Đã xóa bài học!');
        this.dialogRef.close();
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi xóa bài học!');
      },
    });
  }

  
}
