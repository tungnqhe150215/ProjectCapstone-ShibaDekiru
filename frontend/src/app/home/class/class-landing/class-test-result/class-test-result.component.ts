import {Component, OnInit} from '@angular/core';
import {Class} from "../../../../core/models/class";
import {TestAssign} from "../../../../core/models/test-assign";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentClassService} from "../../student-class.service";
import {ClassTestService} from "../../class-test.service";
import {Test} from "../../../../core/models/test";
import {TestResult} from "../../../../core/models/test-result";
import {StudentTestResultService} from "../../../student-test/student-test-result.service";
import {SessionStorageService} from "../../../../shared/services/session-storage.service";
import {StudentTestService} from "../../../student-test/student-test.service";
import {data} from "autoprefixer";
import {TestSection} from "../../../../core/models/test-section";

@Component({
  selector: 'app-class-test-result',
  templateUrl: './class-test-result.component.html',
  styleUrls: ['./class-test-result.component.css']
})
export class ClassTestResultComponent implements OnInit {

  classNow: Class = new Class();

  listTestAssign: TestAssign[] = [];

  draftTestResult: TestResult[] = [];

  allTestResult: TestResult[] = [];

  section: TestSection = new TestSection()

  classId!: number;

  isExpanded: boolean = false;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private studentClass: StudentClassService,
              private studentTestResult: StudentTestResultService,
              private studentTest: StudentTestService,
              private sessionStorage: SessionStorageService,
              private classTest: ClassTestService) {

  }

  ngOnInit(): void {
    this.getListTest();
  }

  toggleExpand() {
    this.isExpanded = !this.isExpanded;
  }

  getListTest() {
    this.classId = this.route.snapshot.params['id'];
    this.studentClass.getClassById(this.classId).subscribe(data => {
      this.classNow = data
      console.log(data)
    })
    this.classTest.getTestByClass(this.classId).subscribe(data => {
      this.listTestAssign = data
      console.log(data)
      this.listTestAssign.forEach(value => {
        this.getTestResultByTest(value.test.testId);
      })
      console.log(this.allTestResult)
    })
  }

  getTestResultByTest(testId: number) {
    const student = this.sessionStorage.getJsonData("auth-user");
    this.studentTestResult.getTestResultByStudentAndTest(student.userAccountId, testId).subscribe(data => {
      this.draftTestResult = data
      console.log(this.draftTestResult)
      console.log(data)
      this.draftTestResult.forEach(value => {
        this.allTestResult.push(value)
      })
      console.log(this.allTestResult)
    })
  }

  getTestResultDataByTestId(testId: number): TestResult[] {
    const listTestResult = this.allTestResult.filter(value => value.testSection?.test.testId === testId)

    return listTestResult
  }

  displayResultForEachTestType(testId: number, type: string): { correctAnswer: number, numberOfQuestion: number } {
    const listResult = this.allTestResult.filter(value => value.testSection?.test.testId === testId && value.testSection.sectionType === type)
    console.log(listResult)
    let correctAnswer = 0;
    let totalQuestion = 0;
    listResult.forEach(value => {
      totalQuestion += value.numberOfQuestion
      correctAnswer += value.result
    })
    console.log(totalQuestion)
    return {correctAnswer: correctAnswer, numberOfQuestion: totalQuestion};
  }

  summaryTestResult(testId: number): { mark: number } {
    let mark: number
    let totalTestCorrectAnswer: number
    let totalTestQuestion: number
    const readingResult = this.displayResultForEachTestType(testId, "READING")
    const listeningResult = this.displayResultForEachTestType(testId,"LISTENING")
    const grammarResult = this.displayResultForEachTestType(testId,"GRAMMAR_VOCAB")
    totalTestCorrectAnswer = readingResult.correctAnswer + listeningResult.correctAnswer + grammarResult.correctAnswer
    totalTestQuestion = readingResult.numberOfQuestion + listeningResult.numberOfQuestion + grammarResult.numberOfQuestion
    if (totalTestQuestion > 0){
      mark = Math.round(totalTestCorrectAnswer/totalTestQuestion*100)/10
    } else {
      mark = 0;
    }
    return {mark: mark}
  }
}
