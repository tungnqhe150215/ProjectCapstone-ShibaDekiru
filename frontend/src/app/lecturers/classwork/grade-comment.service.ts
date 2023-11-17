import { Injectable } from '@angular/core';
import {WritingExerciseAnswer} from "../../core/models/writing-exercise-answer";
import {WritingExercise} from "../../core/models/writing-exercise";
import {Exercise} from "../../core/models/exercise";

@Injectable({
  providedIn: 'root'
})
export class GradeCommentService {

  private writingGrade: { [writingAnswerId: number]: { userAnswer: string, exerciseId:number,mark:number,comment: string } } = {};

  getAnswer(questionId: number): { userAnswer: string, exerciseId:number,mark:number,comment: string } {
    return this.writingGrade[questionId] || { userAnswer: '', exerciseId: null };
  }

  setAnswer(writingAnswerId: number, userAnswer: string, exerciseId:number,mark:number,comment: string): void {
    this.writingGrade[writingAnswerId] = { userAnswer,exerciseId,mark,comment };
  }

  getAllCommentByExercise(exerciseId:number): { userAnswer: string, exerciseId:number,mark:number,comment: string }[] {
    return Object.values(this.writingGrade).filter(answer => answer.exerciseId === exerciseId);
  }


  getAllAnswers(): { userAnswer: string, exerciseId:number,mark:number,comment: string }[] {
    return Object.values(this.writingGrade);
  }

  getTypeSummary(exerciseId:number): { totalQuestions: number } {
    const allAnswers = this.getAllCommentByExercise(exerciseId);
    const totalQuestions = allAnswers.length;
    return { totalQuestions };
  }

  clearAllAnswers(): void {
    this.writingGrade = {};
  }

  initializeAnswers(exerciseAnswer: WritingExerciseAnswer[]): void {
    console.log('đã ở đây')
    console.log(exerciseAnswer)
    exerciseAnswer.forEach(value => {
      this.setAnswer(value.writingExerciseAnswerId,value.answer,value.writingExercise.exercise.exerciseId,value.mark || 0,value.comment || '')
      console.log(this.getAnswer(value.writingExerciseAnswerId))
    })
  }
}
