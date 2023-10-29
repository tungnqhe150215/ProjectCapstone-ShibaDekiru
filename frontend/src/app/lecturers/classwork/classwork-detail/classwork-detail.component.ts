import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { ClassworkService } from '../classwork.service';
import { ClassWork } from 'src/app/core/models/class-work';


@Component({
  selector: 'app-classwork-detail',
  templateUrl: './classwork-detail.component.html',
  styleUrls: ['./classwork-detail.component.css']
})
export class ClassworkDetailComponent implements OnInit{


  classWork: ClassWork = new ClassWork;
  constructor(
    private route: ActivatedRoute,
    public dialogRef: MatDialogRef<ClassworkDetailComponent>,
    private classWorkService: ClassworkService,
    private notifiService: NotificationService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    ){

    }
  ngOnInit(): void {
    this.classWork = new ClassWork();
    this.classWorkService.getClassWorkByID(this.data).subscribe( res=>{
     this.classWork = res
    })
  }

  openExercise(classWorkId: number) {
    this.router.navigate(['lecturer/class/class-work/',classWorkId,'exercise'])
  }
}
