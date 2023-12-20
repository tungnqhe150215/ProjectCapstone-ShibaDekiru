import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LectureClassService } from '../../class/lecture-class.service';
import { ClassworkService } from '../classwork.service';

@Component({
  selector: 'app-delete-classwork',
  templateUrl: './delete-classwork.component.html',
  styleUrls: ['./delete-classwork.component.css']
})
export class DeleteClassworkComponent implements OnInit{

  ngOnInit(): void {
    
  }
  constructor(
    private classWorkService: ClassworkService,
    private notifiService: NotificationService,
    private letcureClassService: LectureClassService,
    private router: Router,
    private route:ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<DeleteClassworkComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number,
    
  ){}

  deleteClassWork(id:number){
    this.classWorkService.deleteClassWork(id).subscribe({
      next: (res) => {
        this.notifiService.openSnackBar('Đã xóa bài học trên lớp');
        // this.getClassWorkList();
      },
      error: console.log,
    })
  }
}
