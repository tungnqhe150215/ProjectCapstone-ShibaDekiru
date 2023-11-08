import { Component, OnInit, ViewChild } from '@angular/core';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Router } from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {ActivatedRoute, Route} from "@angular/router";
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import {MatRippleModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import { ClassworkService } from './classwork.service';
import { ClassWork } from 'src/app/core/models/class-work';
import { AddClassworkComponent } from './add-classwork/add-classwork.component';
import { UpdateClassworkComponent } from './update-classwork/update-classwork.component';
import { ClassworkDetailComponent } from './classwork-detail/classwork-detail.component';
import {Class} from "../../core/models/class";
import {LectureClassService} from "../class/lecture-class.service";
import {data} from "autoprefixer";
import { StorageService } from 'src/app/home/auth/user-login/storage.service';



@Component({
  selector: 'app-classwork',
  templateUrl: './classwork.component.html',
  styleUrls: ['./classwork.component.css']
})
export class ClassworkComponent implements OnInit{

  classWork: ClassWork[] = [];

  aClass: Class = new Class();

  idU!:number;
  currentUser: any;
  

  displayedColumns: string[] = ['id', 'Name', 'CreateAt', 'Deadline','Status','ClassID','Action'];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(
    private classWorkService: ClassworkService,
    private notifiService: NotificationService,
    private letcureClassService: LectureClassService,
    private router: Router,
    private route:ActivatedRoute,
    public dialog: MatDialog,
    private storageService: StorageService,
  ){}


  ngOnInit(): void {
    this.getClassWorkList();
  }

  
  getClassWorkList(){
    this.idU = this.route.snapshot.params['id'];
    this.letcureClassService.getClassById(this.idU).subscribe(data => {
      this.aClass = data
    })
    this.classWorkService.getAllClassWorkInClass(this.idU).subscribe({
      next:(res) =>{
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: console.log,
    });
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openCreateClassWorkDialog(idU:number){
    this.dialog.open(AddClassworkComponent, {
      data:idU
    }).afterClosed().subscribe(() => this.getClassWorkList())
  }

  disableClassWork(disableClassWork: ClassWork){
    this.classWorkService.disableClassWork(disableClassWork.classWorkId).subscribe( () =>{
      disableClassWork.isLocked = !disableClassWork.isLocked;
      this.notifiService.openSnackBar('Change status successful','OK');
    })
  }


  deleteClassWork(id:number){
    this.classWorkService.deleteClassWork(id).subscribe({
      next: (res) => {
        this.notifiService.openSnackBar('Classwork deleted!', 'Ok');
        this.getClassWorkList();
      },
      error: console.log,
    })
  }

  openUpdateClassWorkDialog(id:number){
    this.dialog.open(UpdateClassworkComponent,{
      data:id
    }).afterClosed().subscribe( () => this.getClassWorkList())
  }

  openClassWorkDetailDialog(id:number){
    this.dialog.open(ClassworkDetailComponent,{
      data:id
    }).afterClosed().subscribe( ()=> this.getClassWorkList())
  }

}
