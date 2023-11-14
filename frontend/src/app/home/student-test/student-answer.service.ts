import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StudentAnswerService {

  private answers: { [questionId: number]: { userAnswer: string, correctAnswer: string, sectionId:number,sectionType: string } } = {};

  getAnswer(questionId: number): { userAnswer: string, correctAnswer: string, sectionId:number,sectionType: string } {
    return this.answers[questionId] || { userAnswer: '', correctAnswer: '', sectionId: null,sectionType: '' };
  }

  setAnswer(questionId: number, userAnswer: string, correctAnswer: string,sectionId:number, sectionType: string): void {
    this.answers[questionId] = { userAnswer, correctAnswer, sectionId, sectionType };
  }

  getAllAnswers(type:string): { userAnswer: string, correctAnswer: string, sectionId:number,sectionType: string }[] {
    if (type === 'LISTENING'){
      return Object.values(this.answers).filter(answer => answer.sectionType === 'LISTENING');
    } else
    if (type === 'READING'){
      return Object.values(this.answers).filter(answer => answer.sectionType === 'READING');
    } else
    if (type === 'GRAMMAR_VOCAB'){
      return Object.values(this.answers).filter(answer => answer.sectionType === 'GRAMMAR_VOCAB');
    } else
    return Object.values(this.answers);
  }

  getAllSectionAnswers(sectionId:number): { userAnswer: string, correctAnswer: string, sectionId:number,sectionType: string }[] {
      return Object.values(this.answers).filter(answer => answer.sectionId === sectionId);
  }

  getSectionSummary(sectionId:number): { result: number }{
    const allAnswers = this.getAllSectionAnswers(sectionId);
    const totalQuestions = allAnswers.length;
    const correctAnswers = allAnswers.filter(answer => answer.userAnswer === answer.correctAnswer).length;
    const result = Math.round(correctAnswers/totalQuestions*100)/10
    return {result}
  }


  getTypeSummary(type:string): { totalQuestions: number, correctAnswers: number, incorrectAnswers: number } {
    const allAnswers = this.getAllAnswers(type);
    const totalQuestions = allAnswers.length;
    const correctAnswers = allAnswers.filter(answer => answer.userAnswer === answer.correctAnswer).length;
    const incorrectAnswers = totalQuestions - correctAnswers;

    return { totalQuestions, correctAnswers, incorrectAnswers };
  }
}
