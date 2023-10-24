import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import { Router } from '@angular/router';
import { ClassworkService } from '../classwork.service';
import { ClassWork } from 'src/app/core/models/class-work';
import {MatDatepickerModule} from '@angular/material/datepicker';

@Component({
  selector: 'app-add-classwork',
  templateUrl: './add-classwork.component.html',
  styleUrls: ['./add-classwork.component.css']
})
export class AddClassworkComponent implements OnInit {
  classWork: ClassWork = new ClassWork();
  idU:number = 1;
  constructor(
    private classWorkService: ClassworkService,
    private notifiService: NotificationService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    public dialogRef: MatDialogRef<AddClassworkComponent>,
  ) { }
  ngOnInit(): void { }

  createClassWork(){
    this.classWorkService.createClassWork(this.idU, this.classWork).subscribe(data =>{
      console.log(data);
      this.notifiService.openSnackBar('Create Class Work Done','Cancel');
      this.dialogRef.close();
    })
  }
}
