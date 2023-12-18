import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Class} from "../../../core/models/class";
import {AdminManageClassService} from "../admin-manage-class.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {Lesson} from "../../../core/models/lesson";
import {LessonService} from "../../../core/services/lesson.service";
import {data} from "autoprefixer";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Lecture} from "../../../core/models/lecture";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {interval, Subscription} from "rxjs";
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-list-class',
  templateUrl: './list-class.component.html',
  styleUrls: ['./list-class.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule],
})
export class ListClassComponent implements OnInit{
  displayedColumns: string[] = ['id', 'name', 'lecture','status','action'];
  dataSource!: MatTableDataSource<Class> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  class :Class[] = [];
  id!: number;


  constructor(
    private manageClassService:AdminManageClassService,
    private manageLessonService: LessonService,
    private router:Router,
    private route:ActivatedRoute,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.getClass();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getClass(){
    this.class = []
    this.manageClassService.getClassByLesson(this.id).subscribe(data => {
      this.class = data;
      this.dataSource = new MatTableDataSource(this.class);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
  }

  openDeleteClassDialog(id:number){
    this.dialog.open(ClassDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getClass());
  }

  openCreateClassDialog(id:number){
    this.dialog.open(ClassCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getClass());
  }

  openUpdateClassDialog(id:number){
    this.dialog.open(ClassUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getClass());
  }

  getClassDetail(id:number){
    this.router.navigate(['admin/class',id]);
  }
}

@Component({
  selector: 'app-class-delete-dialog',
  templateUrl: 'class-delete-dialog.html',
  styleUrls: ['./list-class.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class ClassDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<ClassDeleteDialog>,
    private manageClassService:AdminManageClassService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService 
  ) {}
  deleteClass(id:number){
    this.manageClassService.deleteClass(id).subscribe(data => {
      this.dialogRef.close();
    })
    this.nofiService.openSnackBar('Đã xóa lớp học!!');
    // this._snackBar.open('Đã xóa lớp học!!', 'Close', {
    //   duration: 2000,
    //   horizontalPosition: 'right',
    //   verticalPosition: 'top',
    // });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-class-create-dialog',
  templateUrl: 'class-create-dialog.html',
  styleUrls: ['./list-class.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule],
})
export class ClassCreateDialog {

  class:Class =  new Class;
  lecture: Lecture = new Lecture();
  constructor(
    public dialogRef: MatDialogRef<ClassCreateDialog>,
    private manageClassService:AdminManageClassService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService 
  ) {}

  createClass(){
    console.log(this.class)
    this.class.lecture = this.lecture;
    this.manageClassService.createClass(this.data,this.class).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this.nofiService.openSnackBar('Tạo lớp học thành công');
    // this._snackBar.open('Tạo lớp học thành công', 'Đóng', {
    //   duration: 2000,
    //   horizontalPosition: 'right',
    //   verticalPosition: 'top',
    // });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-class-update-dialog',
  templateUrl: 'class-update-dialog.html',
  styleUrls: ['./list-class.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule,MatSlideToggleModule],
})
export class ClassUpdateDialog implements OnInit{

  class:Class =  new Class;
  lecture:Lecture = new Lecture;

  constructor(
    public dialogRef: MatDialogRef<ClassUpdateDialog>,
    private manageClassService:AdminManageClassService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService 
  ) {}

  ngOnInit(): void {
    this.class = new Class();
    this.manageClassService.getClassById(this.data).subscribe(e => {
      this.class = e
      this.lecture = this.class.lecture
    })
  }

  updateClass(){
    console.log(this.class)
    this.manageClassService.updateClass(this.data,this.class).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this.nofiService.openSnackBar('Cập nhật lớp học thành công');
    // this._snackBar.open('Cập nhật lớp học thành công', 'Đóng', {
    //   duration: 2000,
    //   horizontalPosition: 'right',
    //   verticalPosition: 'top',
    // });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
