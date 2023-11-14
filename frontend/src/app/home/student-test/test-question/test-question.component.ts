import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {QuestionBank} from "../../../core/models/question-bank";
import {StudentAnswerService} from "../student-answer.service";
import {TestSection} from "../../../core/models/test-section";

@Component({
  selector: 'app-student-test-question',
  templateUrl: './test-question.component.html',
  styleUrls: ['./test-question.component.css']
})
export class TestQuestionComponent implements  OnChanges {
  @Input() question!: QuestionBank

  @Input() section!: TestSection

  selectedAnswer: string = ''// Lựa chọn đáp án mặc định

  constructor(private studentAnswer: StudentAnswerService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
        if (changes['question'] && changes['question'].currentValue !== changes['question'].previousValue){
          this.selectedAnswer = this.getAnswer().userAnswer;
        }
    }

  onAnswerSelected(answer: string) {
    this.selectedAnswer = answer;
    this.setAnswer(answer);
  }

  getAnswer(): { userAnswer: string, correctAnswer: string, sectionId:number,sectionType: string } {
    console.log(this.studentAnswer.getAnswer(this.question.questionBankId))
    return this.studentAnswer.getAnswer(this.question.questionBankId);
  }

  setAnswer(userAnswer: string): void {
    const correctAnswer = this.question.correctAnswer as string; // Thay thế bằng giá trị đáp án đúng thực tế
    this.studentAnswer.setAnswer(this.question.questionBankId, userAnswer, correctAnswer,this.section.sectionId, this.section.sectionType);
  }
}
