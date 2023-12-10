import { Component, OnInit } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LessonService } from 'src/app/core/services/lesson.service';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-create-vocabulary',
  templateUrl: './create-vocabulary.component.html',
  styleUrls: ['./create-vocabulary.component.css'],
})
export class CreateVocabularyComponent implements OnInit {
  vocabulary: Vocabulary = new Vocabulary();
  lesson: Lesson[] =[];
  constructor(
    private vocabularyService: VocabularyService,
    private router: Router,
    private nofiService: NotificationService, 
    private lessonService: LessonService,
  ) {}
  ngOnInit(): void {
    this.getLesson();
  }

  saveVocabulary() {
    this.vocabularyService.createVocabulary(this.vocabulary).subscribe(
      (data) => {
        console.log(data);
        this.goToVocabulariesList();
        this.nofiService.openSnackBar('Tạo từ mới thành công')
      },
      (error) => {
        console.log(error);
        this.nofiService.openSnackBar('Đã xảy ra lỗi trong quá trình tạo tự vựng');
      }
    );
  }

  private getLesson(){
    this.lessonService.getLessonNoneID().subscribe( data =>{
      this.lesson = data;
      console.log(data)
    })
  
  }

  goToVocabulariesList() {
    this.router.navigate(['admin/list-vocabulary']);
  }
  onSubmit() {
    console.log(this.vocabulary);
    this.saveVocabulary();
  }
}
