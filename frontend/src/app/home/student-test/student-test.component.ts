import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription, timer} from "rxjs";
import {TestResult} from "../../core/models/test-result";
import {Test} from "../../core/models/test";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentTestService} from "./student-test.service";
import {data} from "autoprefixer";
import {TestSection} from "../../core/models/test-section";
import {StudentAnswerService} from "./student-answer.service";
import {SessionStorageService} from "../../shared/services/session-storage.service";
import {Student} from "../../core/models/student";
import {StudentTestResultService} from "./student-test-result.service";
import {CurrentTimeFormatService} from "../../shared/services/current-time-format.service";
import {QuestionBank} from "../../core/models/question-bank";
import {TestAssign} from "../../core/models/test-assign";

@Component({
  selector: 'app-student-test',
  templateUrl: './student-test.component.html',
  styleUrls: ['./student-test.component.css','../home-style.css']
})
export class StudentTestComponent implements OnInit,OnDestroy{

  sectionResult: TestResult[] = [];
  test:Test = new Test();
  testSection:TestSection[] = [];
  testId!: number;
  classId!: number;
  listeningSection:TestSection[] = [];
  readingSection:TestSection[] = [];
  grammarVocabSection:TestSection[] = [];
  draftResult: TestResult = new TestResult();
  student: Student = new Student();
  allQuestion:QuestionBank[] = [];
  testAssign: TestAssign = new TestAssign();
  // Xử lý sự kiện khi người dùng chọn đáp án
  displayCountdown!: string;

  private countdownSubscription: Subscription = new Subscription();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private  testService: StudentTestService,
              private  answerService: StudentAnswerService,
              private  sessionStorage: SessionStorageService,
              private  testResult:StudentTestResultService,
              private  formatDate: CurrentTimeFormatService) {
  }

  ngOnInit() {
    this.getTestInfor();
  }

  getTestInfor(){
    const user = this.sessionStorage.getJsonData('auth-user');
    this.student.studentId = user.userAccountId;
    this.classId =  this.route.snapshot.params['classId'];
    this.testId = this.route.snapshot.params['testId'];
    this.testService.getTestById(this.testId).subscribe(data => {
      this.test = data;
      const duration = this.test.duration * 60 ; // Đặt thời gian đếm ngược trong giây (ví dụ: 5 phút)
      this.startCountdown(duration);
    })
    this.testService.getTestSectionByTest(this.testId).subscribe(data => {
      this.testSection = data
      this.listSection();
      this.testService.getQuestionByTest(this.testId).subscribe(data => {
        this.allQuestion = data
        this.getDefaultAnswer();
      })
    })
    this.testService.getTestAssignByClassAndTest(this.classId,this.testId).subscribe(data => {
      this.testAssign = data
      console.log(data)
      if (new Date(this.testAssign.accessExpirationDate) < new Date()){
        this.router.navigate(['**']);
      }
    })
  }

  ngOnDestroy() {
    this.countdownSubscription.unsubscribe();
  }

  private startCountdown(duration: number): void {
    const endTime = new Date().getTime() + duration * 1000;

    this.countdownSubscription = timer(0, 1000).subscribe(() => {
      const currentTime = new Date().getTime();
      const remainingTime = endTime - currentTime;

      if (remainingTime <= 0) {
        this.displayCountdown = '00:00:00';
        this.countdownSubscription.unsubscribe();
        this.submitTest();
      } else {
        const hours = Math.floor(remainingTime / 3600000);
        const minutes = Math.floor((remainingTime % 3600000) / 60000);
        const seconds = Math.floor((remainingTime % 60000) / 1000);

        this.displayCountdown = this.formatTime(hours) + ':' + this.formatTime(minutes) + ':' + this.formatTime(seconds);
      }
    });
  }

  getDefaultAnswer(){
    this.answerService.initializeAnswers(this.allQuestion,this.testSection);
    console.log(this.answerService.getAnswer(6));
  }

  private formatTime(time: number): string {
    return time < 10 ? '0' + time : '' + time;
  }

  listSection(){
    this.testSection.forEach((testSection) => {
      if (testSection.sectionType === "LISTENING" ) {
        this.listeningSection.push(testSection);
      }
      if (testSection.sectionType === "READING" ) {
        this.readingSection.push(testSection);
      }
      if (testSection.sectionType === "GRAMMAR_VOCAB" ) {
        this.grammarVocabSection.push(testSection);
      }
    })
  }

  goToResultPage(){
    this.router.navigate(['c',this.classId,'t',this.testId,'result'])
  }
  //prevent copy paste event
  onCopy(event: ClipboardEvent): void {
    event.preventDefault();
  }

  createTestResult(data: TestSection){
    this.draftResult = new TestResult();
    this.draftResult.student = this.student
    this.draftResult.testSection = data
    this.draftResult.result = this.answerService.getSectionSummary(data.sectionId).result;
    this.draftResult.numberOfQuestion = this.answerService.getSectionSummary(data.sectionId).totalQuestions
    this.draftResult.classTestAssign = this.testAssign
    console.log(this.draftResult)
    this.testResult.createTestResult(this.draftResult).subscribe(data => {
      console.log(data)
    });
  }

  submitTest() {
    this.testResult.deleteTestResult(this.student.studentId,this.testAssign.id).subscribe(data => {
      console.log('done');
    })
    this.testSection.forEach(data =>
    {
      this.createTestResult(data);
    })
    this.goToResultPage()
  }
}
