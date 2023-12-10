import { Component } from '@angular/core';
import { Grammar } from 'src/app/core/models/grammar';
import { GrammarService } from '../grammar.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LessonService } from 'src/app/core/services/lesson.service';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-update-grammar',
  templateUrl: './update-grammar.component.html',
  styleUrls: ['./update-grammar.component.css']
})
export class UpdateGrammarComponent {
  'id': number;
  grammar: Grammar = new Grammar();
  lesson: Lesson[] =[];
  constructor(
    private grammarService: GrammarService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService, 
    private lessonService: LessonService,
  ) {}
  ngOnInit(): void {
    this.getLesson();
    this.id = this.route.snapshot.params['id'];

    this.grammarService.getGrammarByID(this.id).subscribe(
      (data) => {
        this.grammar = data;
      },
      (error) => {
        console.log(error);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi lấy thông tin grammar');
      }
    );
  }
  onSubmit() {
    this.grammarService.updateGrammar(this.id, this.grammar).subscribe(
      (data) => {
        this.goToGrammarsList();
        this.nofiService.openSnackBar('Cập nhật ngữ pháp thành công');
      },
      (error) => {
        console.log(error);
        this.nofiService.openSnackBar('Đã xảy ra lỗi trong quá trình cập nhật')
      }
    );
  }

  goToGrammarsList() {
    this.router.navigate(['/admin/list-grammar']);
  }

  private getLesson(){
    this.lessonService.getLessonNoneID().subscribe( data =>{
      this.lesson = data;
      console.log(data)
    })
  
  }
}

