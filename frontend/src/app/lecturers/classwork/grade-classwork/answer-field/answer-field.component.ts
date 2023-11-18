import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Exercise} from "../../../../core/models/exercise";
import {WritingExercise} from "../../../../core/models/writing-exercise";
import {ActivatedRoute} from "@angular/router";
import {ClassworkAnswerService} from "../../../../home/student-classwork/classwork-answer.service";
import {StudentClassworkService} from "../../../../home/class/student-classwork.service";
import {StudentGradeClassworkService} from "../../student-grade-classwork.service";
import {GradeCommentService} from "../../grade-comment.service";
import {WritingExerciseAnswer} from "../../../../core/models/writing-exercise-answer";

@Component({
  selector: 'app-answer-field',
  templateUrl: './answer-field.component.html',
  styleUrls: ['./answer-field.component.css']
})
export class AnswerFieldComponent implements OnInit, OnChanges{
  @Input() exercise: Exercise = new Exercise()
  writingExerciseAnswer: WritingExerciseAnswer[] = []
  studentId!: number

  constructor(private route: ActivatedRoute,
              private answer: GradeCommentService,
              private classwork: StudentGradeClassworkService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['exercise'] && changes['exercise'].previousValue !== changes['exercise'].currentValue){
      this.getWritingExerciseAnswer()
    }
  }

  ngOnInit(): void {
    this.getWritingExerciseAnswer()
  }

  getWritingExerciseAnswer(){
    console.log(this.exercise)
    this.studentId = this.route.snapshot.params['studentId']
    console.log(this.studentId)
    this.classwork.getWritingExerciseAnswerByExerciseAndStudent(this.studentId,this.exercise.exerciseId).subscribe(data => {
      this.writingExerciseAnswer = data
      console.log(this.writingExerciseAnswer)
    })
  }

  getAnswerByAnswerId(writingQuizId: number): { userAnswer: string, exerciseId: number,mark:number,comment:string } {
    console.log()
    return this.answer.getAnswer(writingQuizId) ;
  }

  onCommentChange(newComment: string, writingAnswerId: number): void {
    const answer = this.answer.getAnswer(writingAnswerId) ;
    answer.comment = newComment;
    console.log(newComment)
    this.setAnswer(writingAnswerId, answer);
    // Thực hiện các hành động khác nếu cần
  }

  onResultChange(newResult: number, writingAnswerId: number): void {
    const answer = this.answer.getAnswer(writingAnswerId) ;
    answer.mark = newResult;
    this.setAnswer(writingAnswerId, answer);
    // Thực hiện các hành động khác nếu cần
  }

  setAnswer(writingQuizId: number, answer: { userAnswer: string, exerciseId: number,mark:number,comment:string }): void {
    this.answer.setAnswer(writingQuizId,answer.userAnswer,answer.exerciseId,answer.mark,answer.comment);
    // Thực hiện các hành động khác nếu cần
  }
}
