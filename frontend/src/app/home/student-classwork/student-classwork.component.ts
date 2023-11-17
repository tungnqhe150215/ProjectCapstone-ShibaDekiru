import {Component, OnDestroy, OnInit} from '@angular/core';
import {Exercise} from "../../core/models/exercise";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentClassworkService} from "../class/student-classwork.service";
import {ClassworkAnswerService} from "./classwork-answer.service";
import {data} from "autoprefixer";
import {ClassWork} from "../../core/models/class-work";
import {WritingExercise} from "../../core/models/writing-exercise";
import {TestSection} from "../../core/models/test-section";
import {TestResult} from "../../core/models/test-result";
import {WritingExerciseAnswer} from "../../core/models/writing-exercise-answer";
import {SessionStorageService} from "../../shared/services/session-storage.service";
import {Student} from "../../core/models/student";
import {StudentClassWork} from "../../core/models/student-class-work";

@Component({
  selector: 'app-student-classwork',
  templateUrl: './student-classwork.component.html',
  styleUrls: ['./student-classwork.component.css','../home-style.css']
})
export class StudentClassworkComponent implements OnInit,OnDestroy{

  exerciseList: Exercise[] = []
  classwork: ClassWork = new ClassWork()
  classworkId!: number
  classId!:number
  selectedExercise: Exercise | undefined
  writingExercise: WritingExercise[] = []
  answerList:WritingExerciseAnswer[] = []
  draftAnswer!: WritingExerciseAnswer;
  student: Student = new Student();
  draftStudentClassWork: StudentClassWork = new StudentClassWork();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private classworkService: StudentClassworkService,
              private answer: ClassworkAnswerService,
              private sessionStorage: SessionStorageService) {
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.getExercise()
  }

  getExercise(){
    const user = this.sessionStorage.getJsonData('auth-user');
    this.student.studentId = user.userAccountId;
    this.classId = this.route.snapshot.params['classId'];
    this.classworkId = this.route.snapshot.params['classworkId'];
    this.classworkService.getClassWorkById(this.classworkId).subscribe(data => {
      this.classwork = data
    })
    this.classworkService.getExerciseByClasswork(this.classworkId).subscribe(data =>{
      this.exerciseList = data
      this.classworkService.getWritingExerciseByCLasswork(this.classworkId).subscribe(data => {
        this.writingExercise = data
        console.log(this.writingExercise)
        console.log(this.exerciseList)
        this.classworkService.getWritingExerciseAnswerByClassworkAndStudent(user.userAccountId,this.classworkId).subscribe(data =>{
          console.log(data)
          if (data.length > 0){
            console.log('có data')
            this.answer.initializeAnswers(data)
          } else {
            console.log('không có data')
            this.answer.initializeKeys(this.writingExercise,this.exerciseList)
          }
          console.log(this.answer.getAllAnswers())
        })
      })
    })
  }

  goToExercise(exerciseId: number) {
    this.selectedExercise = new Exercise();
    this.selectedExercise = this.exerciseList.find(data => data.exerciseId === exerciseId) as Exercise
  }

  createExerciseAnswer(data: WritingExercise){
    this.draftAnswer = new WritingExerciseAnswer();
    this.draftAnswer.student = this.student
    this.draftAnswer.writingExercise = data
    this.draftAnswer.answer = this.answer.getAnswer(data.writingQuizId).userAnswer;
    console.log(this.draftAnswer)
    this.classworkService.createWritingExerciseAnswer(this.draftAnswer).subscribe(data => {
      console.log(data)
    });
  }

  createStudentClassWork(){
    this.draftStudentClassWork = new StudentClassWork();
    this.draftStudentClassWork.classWork = this.classwork
    this.draftStudentClassWork.student = this.student
    this.classworkService.createStudentClasswork(this.draftStudentClassWork).subscribe(data => {
      console.log(data)
    })
  }

  submitClasswork(){
    this.writingExercise.forEach(data =>
    {
      this.createExerciseAnswer(data);
    })
    this.createStudentClassWork()
    this.goToCompletionPage()
  }

  private goToCompletionPage() {
    this.router.navigate(['c',this.classId,'cw',this.classworkId,'result'])
  }
}
