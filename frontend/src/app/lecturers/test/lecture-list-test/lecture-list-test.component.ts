import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {Test} from "../../../core/models/test";
import {LectureManageTestService} from "../lecture-manage-test.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatTabsModule} from "@angular/material/tabs";
import {SharedModule} from "../../../shared/shared.module";
import {Lecture} from "../../../core/models/lecture";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import { StorageService } from 'src/app/home/auth/user-login/storage.service';
import {data} from "autoprefixer";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-lecture-list-test',
  templateUrl: './lecture-list-test.component.html',
  styleUrls: ['./lecture-list-test.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatTabsModule, SharedModule, NgIf],
})
export class LectureListTestComponent implements OnInit{
  displayedColumns: string[] = ['id', 'title','duration','status','action'];
  dataSource!: MatTableDataSource<Test> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  test :Test[] = [];
  lecture: Lecture = new Lecture();
  id!: number;

  constructor(
    private manageTestService:LectureManageTestService,
    private router:Router,
    private route:ActivatedRoute,
    private storageService: StorageService,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }
  currentUser: any;
  ngOnInit(): void {
    this.getTest();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getTest(){
    this.currentUser = this.storageService.getUser();
    this.test = []
    this.manageTestService.getTestByLecture(this.currentUser.userAccountId ).subscribe(data => {
      this.test = data;
      this.dataSource = new MatTableDataSource(this.test);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.test)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteTestDialog(id:number){
    this.dialog.open(TestDeleteDialog, {
      data: id
    }).afterClosed().subscribe(data => { if (data) this.getTest()});
  }

  openCreateTestDialog(id:number){
    this.dialog.open(TestCreateDialog,{
      data: id
    }).afterClosed().subscribe(data => { if (data) this.getTest()});
  }

  openUpdateTestDialog(id:number){
    this.dialog.open(TestUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(data => { if (data) this.getTest()});
  }

  getTestDetail(id:number){
    this.router.navigate(['lecturer/test',id]);
  }

  getTestAssign(testId: number) {
    this.router.navigate(['lecturer/test/'+testId+'/assign']);
  }
}

@Component({
  selector: 'app-test-delete-dialog',
  templateUrl: 'test-delete-dialog.html',
  styleUrls: ['./lecture-list-test.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class TestDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<TestDeleteDialog>,
    private manageTestService:LectureManageTestService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteTest(id:number){
    this.manageTestService.deleteTest(id).subscribe(data => {
      this.dialogRef.close(data);
    })
    this._snackBar.open('Đã xóa bài kiểm tra!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration:2000,
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-test-create-dialog',
  templateUrl: 'test-create-dialog.html',
  styleUrls: ['./lecture-list-test.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule],
})
export class TestCreateDialog implements OnInit{

  test:Test =  new Test;
  lecture:Lecture = new Lecture();

  constructor(
    public dialogRef: MatDialogRef<TestCreateDialog>,
    private manageTestService:LectureManageTestService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  createTest(){
    this.lecture.lectureId = this.data;
    this.test.lecture = this.lecture;
    console.log(this.test)
    this.manageTestService.createTest(this.data,this.test).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Đã thêm bài kiểm tra mới!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration:2000,
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.test.isLocked = false
  }
}
@Component({
  selector: 'app-test-update-dialog',
  templateUrl: 'test-update-dialog.html',
  styleUrls: ['./lecture-list-test.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule],
})
export class TestUpdateDialog implements OnInit{

  test:Test =  new Test;

  constructor(
    public dialogRef: MatDialogRef<TestUpdateDialog>,
    private manageTestService:LectureManageTestService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit(): void {
    this.test = new Test();
    this.manageTestService.getTestById(this.data).subscribe(e => {
      this.test = e
    })
  }

  updateTest(){
    console.log(this.test)
    this.manageTestService.updateTest(this.data,this.test).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Thông tin bài kiểm tra đã được cập nhật!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration:2000,
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
