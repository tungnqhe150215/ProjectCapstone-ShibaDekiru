import {Component, OnInit} from '@angular/core';
import {ClassWork} from "../../../../core/models/class-work";
import {Student} from "../../../../core/models/student";
import {ActivatedRoute} from "@angular/router";
import {StudentClassworkService} from "../../student-classwork.service";
import {StudentClassWork} from "../../../../core/models/student-class-work";
import {SessionStorageService} from "../../../../shared/services/session-storage.service";
import {data} from "autoprefixer";

@Component({
  selector: 'app-class-board',
  templateUrl: './class-board.component.html',
  styleUrls: ['./class-board.component.css']
})
export class ClassBoardComponent implements OnInit{

  classworkList: ClassWork[] = [];
  classId!: number
  student: Student = new Student();
  studentClasswork: StudentClassWork[] = []



  constructor(private route:ActivatedRoute,
              private classwork: StudentClassworkService,
              private sessionStorage:SessionStorageService) {
  }

  ngOnInit(): void {
    this.getData()
  }

  getData(){
    const user = this.sessionStorage.getJsonData('auth-user')
    this.classId = this.route.snapshot.params['id'];
    this.classwork.getStudentClassworkByCLassAndStudent(user.userAccountId,this.classId).subscribe(data => {
      this.studentClasswork = data
      console.log(this.studentClasswork)
    })
  }
}
