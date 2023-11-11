import { Component } from '@angular/core';

@Component({
  selector: 'app-student-test',
  templateUrl: './student-test.component.html',
  styleUrls: ['./student-test.component.css','../home-style.css']
})
export class StudentTestComponent {

  showSidebar = false;

  answers = ['Đáp án A', 'Đáp án B', 'Đáp án C', 'Đáp án D'];

  topics = ['Chủ đề 1', 'Chủ đề 2', 'Chủ đề 3', 'Chủ đề 4'];

  selectedAnswer: string | null = null; // Lựa chọn đáp án mặc định

  // Xử lý sự kiện khi người dùng chọn đáp án
  onAnswerSelected(answer: string) {
    this.selectedAnswer = answer;
  }
}
