import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { NotificationService } from 'src/app/core/services/notification.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ClassworkService } from '../classwork.service';
import { ClassWork } from 'src/app/core/models/class-work';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { Class } from 'src/app/core/models/class';

@Component({
  selector: 'app-add-classwork',
  templateUrl: './add-classwork.component.html',
  styleUrls: ['./add-classwork.component.css']
})
export class AddClassworkComponent implements OnInit {
  classWork: ClassWork = new ClassWork();
  // myID: number = 1;
  idU!:number;
  constructor(
    private classWorkService: ClassworkService,
    private notifiService: NotificationService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    public dialogRef: MatDialogRef<AddClassworkComponent>,
    private route:ActivatedRoute,
  ) { }
  ngOnInit(): void { }

  createClassWork(){
    // this.idU = this.route.snapshot.params['id'];
    this.classWorkService.createClassWork(this.data, this.classWork).subscribe(data =>{
      console.log(data);
      this.notifiService.openSnackBar('Tạo bài tập thành công');
      this.dialogRef.close();
    })
  }
}
