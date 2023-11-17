import { Component } from '@angular/core';
import {StudentTestResultService} from "../../student-test/student-test-result.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {StudentAnswerService} from "../../student-test/student-answer.service";
import {StudentClassWork} from "../../../core/models/student-class-work";
import {Student} from "../../../core/models/student";
import {StudentClassworkService} from "../../class/student-classwork.service";
import {ClassworkAnswerService} from "../classwork-answer.service";

@Component({
  selector: 'app-classwork-complete',
  templateUrl: './classwork-complete.component.html',
  styleUrls: ['./classwork-complete.component.css','../../home-style.css']
})
export class ClassworkCompleteComponent {

  studentClasswork: StudentClassWork = new StudentClassWork();
  classworkId!: number
  classId!:number
  student:Student = new Student();

  constructor(private studentClassworkService: StudentClassworkService,
              private router: Router,
              private route: ActivatedRoute,
              private sessionStorage: SessionStorageService,
              private studentAnswer: ClassworkAnswerService) {
  }

  ngOnDestroy(): void {
    this.studentAnswer.clearAllAnswers();
  }

  ngOnInit(): void {
    this.getData();
  }


  getData(){
    const user = this.sessionStorage.getJsonData('auth-user');
    this.classworkId = this.route.snapshot.params['classworkId'];
    this.classId = this.route.snapshot.params['classId'];
    this.studentClassworkService.getStudentClassworkByCLassworkAndStudent(user.userAccountId,this.classworkId).subscribe(data => {
      this.studentClasswork = data
      console.log(this.studentClasswork)
    })
  }

  backToClass() {
    this.router.navigate(['class',this.classId]);
  }

  backToClassWork() {
    this.router.navigate(['c',this.classId,'cw',this.classworkId,'do']);
  }
}
