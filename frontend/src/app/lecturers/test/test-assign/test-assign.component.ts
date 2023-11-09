import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatSort, MatSortModule} from "@angular/material/sort";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {TestAssign} from "../../../core/models/test-assign";
import {LectureManageTestAssignService} from "../lecture-manage-test-assign.service";
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
import {Test} from "../../../core/models/test";
import {Class} from "../../../core/models/class";
import {LectureClassService} from "../../class/lecture-class.service";
import {MatSelectModule} from "@angular/material/select";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {data} from "autoprefixer";
import {NgForOf} from "@angular/common";
import {LectureManageTestService} from "../lecture-manage-test.service";

@Component({
  selector: 'app-test-assign',
  templateUrl: './test-assign.component.html',
  styleUrls: ['./test-assign.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, MatSortModule, MatPaginatorModule, MatButtonModule, MatIconModule, MatTabsModule, SharedModule],
})
export class TestAssignComponent implements OnInit{
  displayedColumns: string[] = ['id','className','expiredDate','action'];
  dataSource!: MatTableDataSource<TestAssign> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  testAssign :TestAssign[] = [];
  test: Test = new Test();
  id!: number;
  user:any

  constructor(
    private manageTestAssignService:LectureManageTestAssignService,
    private manageTestService: LectureManageTestService,
    private router:Router,
    private route:ActivatedRoute,
    private sessionStorage: SessionStorageService,
    public dialog: MatDialog) {
    // Assign the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.user = this.sessionStorage.getJsonData("auth-user")
    this.getTestAssign();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getTestAssign(){
    this.id = this.route.snapshot.params['id'];
    this.test = new Test();
    this.manageTestService.getTestById(this.id).subscribe(data => {
      this.test = data
    })
    this.testAssign = []
    this.manageTestAssignService.getTestAssignByTest(this.id).subscribe(data => {
      this.testAssign = data;
      this.dataSource = new MatTableDataSource(this.testAssign);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.testAssign)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }

  openDeleteTestAssignDialog(id:number){
    this.dialog.open(TestAssignDeleteDialog, {
      data: id
    }).afterClosed().subscribe(() => this.getTestAssign());
  }

  openCreateTestAssignDialog(id:number){
    this.dialog.open(TestAssignCreateDialog,{
      data: id
    }).afterClosed().subscribe(() => this.getTestAssign());
  }

  openUpdateTestAssignDialog(id:number){
    this.dialog.open(TestAssignUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(() => this.getTestAssign());
  }

  getTestAssignDetail(id:number){
    this.router.navigate(['lecturer/test/'+ id + '/result']);
  }
}

@Component({
  selector: 'app-test-assign-delete-dialog',
  templateUrl: 'test-assign-delete-dialog.html',
  styleUrls: ['./test-assign.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class TestAssignDeleteDialog {
  constructor(
    public dialogRef: MatDialogRef<TestAssignDeleteDialog>,
    private manageTestAssignService:LectureManageTestAssignService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}
  deleteTestAssign(id:number){
    this.manageTestAssignService.deleteTestAssign(id).subscribe(data => {
      this.dialogRef.close();
    })
    this._snackBar.open('Deleted!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'app-test-assign-create-dialog',
  templateUrl: 'test-assign-create-dialog.html',
  styleUrls: ['./test-assign.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule, MatSelectModule, NgForOf],
})
export class TestAssignCreateDialog implements OnInit{

  testAssign:TestAssign =  new TestAssign;
  classes:Class[] = [];
  chooseClass:Class = new Class();
  extendTime!: number;
  test:Test = new Test();
  user:any
  classId!: number;

  constructor(
    public dialogRef: MatDialogRef<TestAssignCreateDialog>,
    private manageTestAssignService:LectureManageTestAssignService,
    private manageClassService:LectureClassService,
    private sessionStorageService:SessionStorageService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  ngOnInit() {
    this.user = this.sessionStorageService.getJsonData("auth-user");
    this.manageClassService.getClassByLecture(1).subscribe(data => {
      this.classes = data;
    })
    this.test.testId = this.data;
  }

  createTestAssign(){
    this.chooseClass.id = this.classId
    this.testAssign.test = this.test
    console.log(this.testAssign)
    console.log(this.chooseClass)
    this.manageTestAssignService.createTestAssign(this.data,this.testAssign,this.extendTime).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('New testAssign part added!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
@Component({
  selector: 'app-test-assign-update-dialog',
  templateUrl: 'test-assign-update-dialog.html',
  styleUrls: ['./test-assign.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, MatSlideToggleModule],
})
export class TestAssignUpdateDialog {

  extendTime!: number

  constructor(
    public dialogRef: MatDialogRef<TestAssignUpdateDialog>,
    private manageTestAssignService:LectureManageTestAssignService,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: number,
  ) {}

  updateTestAssign(){
    this.manageTestAssignService.updateTestAssign(this.data,this.extendTime).subscribe(data => {
      console.log(data)
      this.dialogRef.close();
    })
    this._snackBar.open('TestAssign part updated!!', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

