import {Component, OnDestroy, OnInit} from '@angular/core';
import {Exercise} from "../../../core/models/exercise";
import {ClassWork} from "../../../core/models/class-work";
import {WritingExercise} from "../../../core/models/writing-exercise";
import {WritingExerciseAnswer} from "../../../core/models/writing-exercise-answer";
import {Student} from "../../../core/models/student";
import {StudentClassWork} from "../../../core/models/student-class-work";
import {ActivatedRoute, Router} from "@angular/router";
import {StudentClassworkService} from "../../../home/class/student-classwork.service";
import {ClassworkAnswerService} from "../../../home/student-classwork/classwork-answer.service";
import {SessionStorageService} from "../../../shared/services/session-storage.service";
import {GradeCommentService} from "../grade-comment.service";
import {StudentGradeClassworkService} from "../student-grade-classwork.service";
import {data} from "autoprefixer";

@Component({
  selector: 'app-grade-classwork',
  templateUrl: './grade-classwork.component.html',
  styleUrls: ['./grade-classwork.component.css']
})
export class GradeClassworkComponent implements OnInit,OnDestroy{

  exerciseList: Exercise[] = []
  classwork: ClassWork = new ClassWork()
  classworkId!: number
  classId!:number
  selectedExercise: Exercise | undefined
  writingExercise: WritingExercise[] = []
  answerList:WritingExerciseAnswer[] = []
  draftAnswer!: WritingExerciseAnswer;
  gradeStudent: Student = new Student();
  studentId!:number;
  student:Student = new Student();
  draftStudentClassWork: StudentClassWork = new StudentClassWork();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private classworkService: StudentGradeClassworkService,
              private answer: GradeCommentService,
              private sessionStorage: SessionStorageService) {
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.getExercise()
  }

  getExercise(){
    this.studentId = this.route.snapshot.params['studentId'];
    this.student.studentId = this.studentId
    this.classId = this.route.snapshot.params['classId'];
    this.classworkId = this.route.snapshot.params['classworkId'];
    this.classworkService.getClassWorkById(this.classworkId).subscribe(data => {
      this.classwork = data
    })
    this.classworkService.getExerciseByClasswork(this.classworkId).subscribe(data =>{
      this.exerciseList = data
      this.classworkService.getWritingExerciseByCLasswork(this.classworkId).subscribe(data => {
        this.writingExercise = data
      })
    })
    this.classworkService.getWritingExerciseAnswerByClassworkAndStudent(this.studentId,this.classworkId).subscribe(data =>{
      this.answerList = data
      if (this.answerList.length > 0){
        // @ts-ignore
        this.gradeStudent = this.answerList.at(0).student as Student
      }
      this.answer.initializeAnswers(data)
      console.log(this.answer.getAllAnswers())
    })
  }

  goToExercise(exerciseId: number) {
    this.selectedExercise = new Exercise();
    this.selectedExercise = this.exerciseList.find(data => data.exerciseId === exerciseId) as Exercise
  }

  updateExerciseAnswer(data: WritingExerciseAnswer){
    this.draftAnswer = new WritingExerciseAnswer();
    this.draftAnswer.student = this.student
    this.draftAnswer.writingExercise = data.writingExercise
    this.draftAnswer.comment = this.answer.getAnswer(data.writingExerciseAnswerId).comment
    console.log(this.draftAnswer.comment)
    this.draftAnswer.mark = this.answer.getAnswer(data.writingExerciseAnswerId).mark
    this.draftAnswer.answer = this.answer.getAnswer(data.writingExerciseAnswerId).userAnswer;
    console.log(this.draftAnswer)
    this.classworkService.updateWritingExerciseAnswer(this.draftAnswer).subscribe(data => {
      console.log(data)
    });
  }

  updateStudentClassWork(){
    this.draftStudentClassWork = new StudentClassWork();
    this.draftStudentClassWork.classWork = this.classwork
    this.draftStudentClassWork.student = this.student
    this.draftStudentClassWork.result = this.getClassworkTotalMark()
    this.classworkService.updateStudentClasswork(this.draftStudentClassWork).subscribe(data => {
      console.log(data)
    })
  }

  getClassworkTotalMark(){
    let sum = 0
    let grade = 0
    this.answerList.forEach(data => {
        sum += data.writingExercise.mark
    })
    this.answer.getAllAnswers().forEach(data => {
      grade += data.mark as number
      console.log(grade)
    })
    console.log(sum)
    console.log(grade)
    return Math.round((grade/sum)*100)/10
  }

  submitComment(){
    this.answerList.forEach(data =>
    {
      this.updateExerciseAnswer(data);
    })
    this.updateStudentClassWork()
    this.goToCompletionPage()
  }

  private goToCompletionPage() {
    this.answer.clearAllAnswers()
    this.router.navigate(['lecturer/class',this.classId,'cw',this.classworkId,'s'])
  }
}
