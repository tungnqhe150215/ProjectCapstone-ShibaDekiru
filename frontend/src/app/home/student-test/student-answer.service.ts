import { Injectable } from '@angular/core';
import {QuestionBank} from "../../core/models/question-bank";
import {TestSection} from "../../core/models/test-section";

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

  getSectionSummary(sectionId:number): { result: number,totalQuestions: number }{
    const allAnswers = this.getAllSectionAnswers(sectionId);
    const totalQuestions = allAnswers.length;
    const correctAnswers = allAnswers.filter(answer => answer.userAnswer === answer.correctAnswer).length;
    const result = correctAnswers
    return {result, totalQuestions};
  }


  getTypeSummary(type:string): { totalQuestions: number, correctAnswers: number, incorrectAnswers: number } {
    const allAnswers = this.getAllAnswers(type);
    const totalQuestions = allAnswers.length;
    const correctAnswers = allAnswers.filter(answer => answer.userAnswer === answer.correctAnswer).length;
    const incorrectAnswers = totalQuestions - correctAnswers;

    return { totalQuestions, correctAnswers, incorrectAnswers };
  }

  clearAllAnswers(): void {
    this.answers = {};
  }

  initializeAnswers(questionBank: QuestionBank[],section: TestSection[]): void {
    questionBank.forEach((question) => {
      section.forEach(section => {
          if (question.section.sectionId === section.sectionId) {
            this.setAnswer(question.questionBankId, '', question.correctAnswer, question.section.sectionId as number, section.sectionType);
          }
      })
    });
  }
}
