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
import {MatSelectChange, MatSelectModule} from "@angular/material/select";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {data} from "autoprefixer";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {LectureManageTestService} from "../lecture-manage-test.service";

@Component({
  selector: 'app-test-assign',
  templateUrl: './test-assign.component.html',
  styleUrls: ['./test-assign.component.css'],
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
      this.dataSource.filterPredicate = (item: TestAssign, filter: string) => {
        return (
          item.assignedClass.className.toLowerCase().includes(filter)
        );
      };
    })
  }

  openDeleteTestAssignDialog(id:number){
    this.dialog.open(TestAssignDeleteDialog, {
      data: id
    }).afterClosed().subscribe(data => { if (data) this.getTestAssign()});
  }

  openCreateTestAssignDialog(id:number){
    this.dialog.open(TestAssignCreateDialog,{
      data: id
    }).afterClosed().subscribe(data => { if (data) this.getTestAssign()});
  }

  openUpdateTestAssignDialog(id:number){
    this.dialog.open(TestAssignUpdateDialog,
      {
        data: id
      }
    ).afterClosed().subscribe(data => { if (data) this.getTestAssign()});
  }

  getTestAssignDetail(id:number){
    this.router.navigate(['lecturer/test/'+ id + '/result']);
  }
}

@Component({
  selector: 'app-test-assign-delete-dialog',
  templateUrl: 'test-assign-delete-dialog.html',
  styleUrls: ['./test-assign.component.css'],
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
      this.dialogRef.close(data);
    })
    this._snackBar.open('Đã xóa thành công!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
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
    this.manageClassService.getClassByLecture(this.user.userAccountId).subscribe(data => {
      this.classes = data;
    })
    this.test.testId = this.data;
  }

  createTestAssign(){
    this.chooseClass.classId = this.classId
    this.testAssign.test = this.test
    this.testAssign.assignedClass = this.chooseClass
    console.log(this.testAssign)
    console.log(this.chooseClass)
    this.manageTestAssignService.createTestAssign(this.data,this.testAssign,this.extendTime).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Lớp học đã được thêm!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration:2000
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClassSelected(event: any) {
    this.chooseClass = event.value
    console.log('Selected class:', event.value);
  }

  modelChanged(event: any) {
    this.classId = event.value
  }
}
@Component({
  selector: 'app-test-assign-update-dialog',
  templateUrl: 'test-assign-update-dialog.html',
  styleUrls: ['./test-assign.component.css'],
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
    console.log(this.extendTime)
    this.manageTestAssignService.updateTestAssign(this.data,this.extendTime).subscribe(data => {
      console.log(data)
      this.dialogRef.close(data);
    })
    this._snackBar.open('Việc giao bài đã được làm mới!!', 'Đóng', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

