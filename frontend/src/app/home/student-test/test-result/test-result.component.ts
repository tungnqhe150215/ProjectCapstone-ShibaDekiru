import {Component, OnDestroy, OnInit} from '@angular/core';
import {TestResult} from "../../../core/models/test-result";
import {StudentTestResultService} from "../student-test-result.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {StudentAnswerService} from "../student-answer.service";

@Component({
  selector: 'app-test-result',
  templateUrl: './test-result.component.html',
  styleUrls: ['./test-result.component.css', '../../home-style.css']
})
export class TestResultComponent implements OnInit,OnDestroy {

  testResultList: TestResult[] = [];

  testId!: number

  classId!: number

  avarageResult!: number

  constructor(private studentResultService: StudentTestResultService,
              private router: Router,
              private route: ActivatedRoute,
              private sessionStorage: SessionStorageService,
              private studentAnswer: StudentAnswerService) {
  }

  ngOnDestroy(): void {
    this.studentAnswer.clearAllAnswers();
  }

  ngOnInit(): void {
    this.getData();
  }


  getData(){
    const user = this.sessionStorage.getJsonData('auth-user');
    this.testId = this.route.snapshot.params['testId'];
    this.classId = this.route.snapshot.params['classId'];
    this.studentResultService.getTestResultByStudentAndTest(user.userAccountId,this.testId).subscribe(data => {
      this.testResultList = data
    })
    this.getAverageResult();
  }

  getAverageResult(){
    if (this.getTotalQuestion() > 0){
      this.avarageResult = Math.round(this.getTotalCorrectAnswer()/this.getTotalQuestion()*100)/10
    } else this.avarageResult = 0
  }

  getTotalCorrectAnswer() {
    return this.studentAnswer.getTypeSummary('LISTENING').correctAnswers
      + this.studentAnswer.getTypeSummary('READING').correctAnswers
      + this.studentAnswer.getTypeSummary('GRAMMAR_VOCAB').correctAnswers;
  }

  getTotalQuestion() {
    return this.studentAnswer.getTypeSummary('LISTENING').totalQuestions
      + this.studentAnswer.getTypeSummary('READING').totalQuestions
      + this.studentAnswer.getTypeSummary('GRAMMAR_VOCAB').totalQuestions;
  }

  typeLength(type:string){
    return this.studentAnswer.getAllAnswers(type).length
  }

  typeTotalQuestion(type:string){
    return this.studentAnswer.getTypeSummary(type).totalQuestions
  }

  typeCorrectAnswer(type:string){
    return this.studentAnswer.getTypeSummary(type).correctAnswers
  }

  backToClass() {
    this.router.navigate(['class',this.classId]);
  }
}
