import { Injectable } from '@angular/core';
import {QuestionBank} from "../../core/models/question-bank";
import {TestSection} from "../../core/models/test-section";
import {WritingExercise} from "../../core/models/writing-exercise";
import {Exercise} from "../../core/models/exercise";
import {WritingExerciseAnswer} from "../../core/models/writing-exercise-answer";
import {StudentClassworkService} from "../class/student-classwork.service";

@Injectable({
  providedIn: 'root'
})
export class ClassworkAnswerService {

  private writingAnswer: { [writingQuesitonId: number]: { userAnswer: string, exerciseId:number,comment:string, mark:number } } = {};

  getAnswer(questionId: number): { userAnswer: string, exerciseId:number,comment:string, mark:number } {
    return this.writingAnswer[questionId] || { userAnswer: '', exerciseId: null,comment:'',mark:0 };
  }

  setAnswer(questionId: number, userAnswer: string, exerciseId:number,comment:string,mark:number): void {
    this.writingAnswer[questionId] = { userAnswer,exerciseId,comment,mark};
  }

  getAllAnswersByExercise(exerciseId:number): { userAnswer: string, exerciseId:number,comment:string, mark:number  }[] {
    return Object.values(this.writingAnswer).filter(answer => answer.exerciseId === exerciseId);
  }


  getAllAnswers(): { userAnswer: string, exerciseId:number,comment:string, mark:number }[] {
    return Object.values(this.writingAnswer);
  }

  getTypeSummary(exerciseId:number): { totalQuestions: number } {
    const allAnswers = this.getAllAnswersByExercise(exerciseId);
    const totalQuestions = allAnswers.length;
    return { totalQuestions };
  }

  clearAllAnswers(): void {
    this.writingAnswer = {};
  }

  initializeAnswers(exerciseAnswer: WritingExerciseAnswer[]): void {
    console.log('đã ở đây')
    console.log(exerciseAnswer)
    exerciseAnswer.forEach(value => {

      this.setAnswer(value.writingExercise.writingQuizId,value.answer,value.writingExercise.exercise.exerciseId,value.comment || '',value.mark || 0)
      console.log(this.getAnswer(value.writingExercise.writingQuizId))
    })
  }

  initializeKeys(writingQuestions: WritingExercise[],exercises: Exercise[]): void {
    writingQuestions.forEach((question) => {
      exercises.forEach(exercise => {
        if (question.exercise.exerciseId === exercise.exerciseId) {
          this.setAnswer(question.writingQuizId, '', question.exercise.exerciseId as number,'',0);
          console.log(question.writingQuizId)
        }
      })
    });
  }

}
