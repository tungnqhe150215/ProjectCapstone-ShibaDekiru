import {Component, OnInit, ViewChild} from '@angular/core';
import {Class} from "../../../core/models/class";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ClassworkService} from "../classwork.service";
import {NotificationService} from "../../../core/services/notification.service";
import {LectureClassService} from "../../class/lecture-class.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {StorageService} from "../../../home/auth/user-login/storage.service";
import {StudentGradeClassworkService} from "../student-grade-classwork.service";
import {StudentClassWork} from "../../../core/models/student-class-work";
import {ClassWork} from "../../../core/models/class-work";
import {data} from "autoprefixer";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-submission-list',
  templateUrl: './submission-list.component.html',
  styleUrls: ['./submission-list.component.css']
})
export class SubmissionListComponent implements OnInit {

  studentClassWorks: StudentClassWork[] = [];
  aClass: Class = new Class();
  classId!: number
  classWork: ClassWork = new ClassWork();
  classWorkId!: number;
  currentUser: any;


  displayedColumns: string[] = ['id', 'student', 'submitTime', 'status','result','isGraded', 'action'];
  dataSource!: MatTableDataSource<StudentClassWork>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private classWorkService: ClassworkService,
    private studentGrade: StudentGradeClassworkService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
  }


  ngOnInit(): void {
    this.getStudentClassWorkSubmissionList();
  }


  getStudentClassWorkSubmissionList() {
    this.classWorkId = this.route.snapshot.params['classWorkId']
    this.classId = this.route.snapshot.params['classId'];
    this.studentGrade.getStudentClassWorkByClassWork(this.classWorkId).subscribe(data => {
        this.studentClassWorks = data
        this.dataSource = new MatTableDataSource(this.studentClassWorks);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
    })
    this.classWorkService.getClassWorkByID(this.classWorkId).subscribe(data => {
      this.classWork = data
    })
  }

  openStudentClassWork(studentId: any) {
    this.router.navigate(['lecturer/class',this.classId,'cw',this.classWorkId,'s',studentId])
  }

  protected readonly formatDate = formatDate;
}
