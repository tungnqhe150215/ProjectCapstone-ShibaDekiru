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
import {TestAssign} from "../../../core/models/test-assign";
import {LectureManageTestAssignService} from "../lecture-manage-test-assign.service";
import {data} from "autoprefixer";

interface ResultConverter{
  studentId:number;
  studentName:string;
  doneTime:Date;
  mark:number;
}

@Component({
  selector: 'app-test-result',
  templateUrl: './test-result.component.html',
  styleUrls: ['./test-result.component.css']
})
export class TestResultComponent {
  displayedColumns: string[] = ['id','student','date','result'];
  dataSource!: MatTableDataSource<ResultConverter> ;

  studentResult: { [studentId: number]: { studentName: string, mark:number,doneTime:Date } } = {};

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  testResult :TestResult[] = [];
  testAssign: TestAssign = new TestAssign();
  test: Test = new Test();
  id!: number;
  user:any

  constructor(
    private manageTestResultService:LectureManageTestResultService,
    private manageTestService: LectureManageTestService,
    private manageTestAssign: LectureManageTestAssignService,
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
    this.manageTestAssign.getTestAsignById(this.id).subscribe(data => {
      this.testAssign = data;
      this.manageTestResultService.getTestResultByTestAssign(this.testAssign.id).subscribe(data => {
        this.testResult = data;
        this.dataSource = new MatTableDataSource(this.groupByStudent(this.testResult));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        console.log(data)
      },error => {
        this.router.navigate(["**"]);
      })
    }, error => {
      this.router.navigate(['**'])
    })
    // this.manageTestService.getTestById(this.id).subscribe(data => {
    //   this.test = data
    // })
    // this.testResult = []
    // this.manageTestResultService.getTestResultByTest(this.id).subscribe(data => {
    //   this.testResult = data;
    //   this.dataSource = new MatTableDataSource(this.testResult);
    //   this.dataSource.paginator = this.paginator;
    //   this.dataSource.sort = this.sort;
    //   console.log(data)
    // })
    // console.log(this.testResult)
    // console.log(this.id)
  }

  dataConverter(items:TestResult[]){

  }

  private groupByStudent(array: TestResult[]): ResultConverter[] {
    const groupedMap = new Map<number, TestResult[]>();

    array.forEach((item) => {
      const studentId = item.student.studentId;

      if (!groupedMap.has(studentId)) {
        groupedMap.set(studentId, []);
      }
      // @ts-ignore
      groupedMap.get(studentId).push(item);
    });

    const converter: ResultConverter[] = []
    groupedMap.forEach((value, key) => {
      const totalNumberQuestion = value.reduce((acc, curr) => acc + curr.numberOfQuestion, 0);
      const result = value.reduce((acc, curr) => acc + curr.result, 0);
      const studentName = value[0].student.firstName + ' ' + value[0].student.lastName
      const doneTime = value[0].doneTime
      converter.push({studentId: key, studentName: studentName,doneTime: doneTime,mark: Math.round(result/totalNumberQuestion*100)/10});
    });

    return converter;
  }
}
