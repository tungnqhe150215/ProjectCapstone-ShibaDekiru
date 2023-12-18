import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import { ClassWork } from 'src/app/core/models/class-work';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ClassworkService } from '../classwork.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-classwork',
  templateUrl: './update-classwork.component.html',
  styleUrls: ['./update-classwork.component.css']
})
export class UpdateClassworkComponent implements OnInit{

  classWork: ClassWork = new ClassWork;
  constructor(
    public dialogRef: MatDialogRef<UpdateClassworkComponent>,
    private classWorkService: ClassworkService,
    private notifiService: NotificationService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ){}

  ngOnInit(): void {
   this.classWork = new ClassWork();
   this.classWorkService.getClassWorkByID(this.data).subscribe( res => {
    this.classWork = res
   })
    
  }
  updateClassWork(){
    this.classWorkService.updateClassWork(this.data, this.classWork).subscribe(data =>{
      console.log(data)
      this.dialogRef.close();
      this.notifiService.openSnackBar('Cập nhật bài học thành công!');
    })
  }

}
