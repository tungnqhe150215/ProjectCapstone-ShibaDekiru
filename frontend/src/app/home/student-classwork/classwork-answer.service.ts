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

  private writingAnswer: { [writingQuesitonId: number]: { userAnswer: string, exerciseId:number } } = {};

  getAnswer(questionId: number): { userAnswer: string, exerciseId:number } {
    return this.writingAnswer[questionId] || { userAnswer: '', exerciseId: null };
  }

  setAnswer(questionId: number, userAnswer: string, exerciseId:number): void {
    this.writingAnswer[questionId] = { userAnswer,exerciseId };
  }

  getAllAnswersByExercise(exerciseId:number): { userAnswer: string, exerciseId:number }[] {
    return Object.values(this.writingAnswer).filter(answer => answer.exerciseId === exerciseId);
  }


  getAllAnswers(): { userAnswer: string, exerciseId:number }[] {
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

      this.setAnswer(value.writingExercise.writingQuizId,value.answer,value.writingExercise.exercise.exerciseId)
      console.log(this.getAnswer(value.writingExercise.writingQuizId))
    })
  }

  initializeKeys(writingQuestions: WritingExercise[],exercises: Exercise[]): void {
    writingQuestions.forEach((question) => {
      exercises.forEach(exercise => {
        if (question.exercise.exerciseId === exercise.exerciseId) {
          this.setAnswer(question.writingQuizId, '', question.exercise.exerciseId as number);
          console.log(question.writingQuizId)
        }
      })
    });
  }

}
