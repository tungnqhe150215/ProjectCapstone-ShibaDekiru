import {Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {TestSection} from "../../../core/models/test-section";
import {QuestionBank} from "../../../core/models/question-bank";
import {StudentTestService} from "../student-test.service";
import {data} from "autoprefixer";
import {StudentAnswerService} from "../student-answer.service";

@Component({
  selector: 'app-test-section-title',
  templateUrl: './section-title.component.html',
  styleUrls: ['./section-title.component.css']
})
export class SectionTitleComponent implements OnChanges, OnInit {

  @Input() section: TestSection = new TestSection();

  @ViewChild('audioElement', {static: false}) audioElement: ElementRef | undefined;


  questionList: QuestionBank[] = [];

  selectedQuestion: QuestionBank | undefined;

  audioEndedHandler: any

  loop: number = 0;

  constructor(private studentTestService: StudentTestService,
              private answerService: StudentAnswerService) {
  }

  currentAudioSource: string = '';

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['section'] && changes['section'].currentValue !== changes['section'].previousValue) {
      console.log(changes['section'].currentValue)
      console.log(changes['section'].previousValue)
      this.getQuestionList()
      this.firstSelectQuestion()
      this.updateAudioSource();
      this.audioElement?.nativeElement.load();
      this.assignAudioEndedHandler();
    }
  }

  ngOnInit(): void {
    this.getQuestionList()
    this.firstSelectQuestion()
    this.assignAudioEndedHandler();
  }

  updateAudioSource(): void {
    // Dựa vào giá trị của this.section, xác định nguồn audio tương ứng
    if (this.section && this.section.sectionType === 'LISTENING') {
      this.currentAudioSource = this.section.sectionAttach;
    } else {
      this.currentAudioSource = ''; // Đặt giá trị rỗng nếu không phải là LISTENING
    }
  }

  assignAudioEndedHandler(): void {
    this.audioEndedHandler = () => {
      // Xử lý sự kiện khi audio kết thúc
      if (this.audioElement && this.audioElement.nativeElement) {
        this.audioElement.nativeElement.currentTime = 0; // Đặt thời gian trở về 0 để chuẩn bị cho lần phát tiếp theo
        this.audioElement.nativeElement.play(); // Phát lại audio
        this.loop++;
        console.log(this.loop)
      }
    };
    if (this.audioElement && this.audioElement.nativeElement) {
      this.audioElement.nativeElement.addEventListener('ended', this.audioEndedHandler);
    }
  }

  getQuestionList() {
    this.studentTestService.getQuestionBySectionId(this.section.sectionId).subscribe(data => {
      this.questionList = data;
    })
  }

  selectQuestion(id: number) {
    this.selectedQuestion = new QuestionBank();
    this.selectedQuestion = this.questionList.find(data => data.questionBankId === id) as QuestionBank;
    console.log(this.selectedQuestion)
  }

  hasAnswer(questionId:number): boolean {
    return this.answerService.getAnswer(questionId).userAnswer !== '';
  }

  firstSelectQuestion(){
    this.selectedQuestion = undefined;
  }
}
