import {Component, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {TestResult} from "../../../core/models/test-result";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {Test} from "../../../core/models/test";
import {LectureManageTestResultService} from "../lecture-manage-test-result.service";
import {LectureManageTestService} from "../lecture-manage-test.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-test-result',
  templateUrl: './test-result.component.html',
  styleUrls: ['./test-result.component.css']
})
export class TestResultComponent {
  displayedColumns: string[] = ['id','student','date','result'];
  dataSource!: MatTableDataSource<TestResult> ;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  testResult :TestResult[] = [];
  test: Test = new Test();
  id!: number;
  user:any

  constructor(
    private manageTestResultService:LectureManageTestResultService,
    private manageTestService: LectureManageTestService,
    private router:Router,
    private route:ActivatedRoute,
    private sessionStorage: SessionStorageService,
    public dialog: MatDialog) {
    // Result the data to the data source for the table to render
  }

  ngOnInit(): void {
    this.user = this.sessionStorage.getJsonData("auth-user")
    this.getTestResult();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  private getTestResult(){
    this.id = this.route.snapshot.params['id'];
    this.test = new Test();
    this.manageTestService.getTestById(this.id).subscribe(data => {
      this.test = data
    })
    this.testResult = []
    this.manageTestResultService.getTestResultByTest(this.id).subscribe(data => {
      this.testResult = data;
      this.dataSource = new MatTableDataSource(this.testResult);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data)
    })
    console.log(this.testResult)
    console.log(this.id)
    // return this.courseService.getCourseList();
  }
}
