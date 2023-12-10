import { Component } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LessonService } from 'src/app/core/services/lesson.service';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-update-vocabulary',
  templateUrl: './update-vocabulary.component.html',
  styleUrls: ['./update-vocabulary.component.css']
})
export class UpdateVocabularyComponent {
  'id': number;
  lesson: Lesson[] =[];
  vocabulary: Vocabulary = new Vocabulary();
  constructor(
    private vocabularyService: VocabularyService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService,
    private lessonService: LessonService,
  ) {}
  ngOnInit(): void {
    this.getLesson();
    this.id = this.route.snapshot.params['id'];

    this.vocabularyService.getVocabularyByID(this.id).subscribe(
      (data) => {
        this.vocabulary = data;
      },
      (error) => {
        console.log(error)
        this.nofiService.openSnackBar('Đã có lỗi xảy ra khi lấy thông tin')
      }
    );
  }
  onSubmit() {
    this.vocabularyService.updateVocabulary(this.id, this.vocabulary).subscribe(
      (data) => {
        this.goToVocabularyList();
        this.nofiService.openSnackBar('Cập nhật từ mới thành công');
      },
      (error) => {
        console.log(error);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi cập nhập nhật từ mới')
      }
    );
  }

  private getLesson(){
    this.lessonService.getLessonNoneID().subscribe( data =>{
      this.lesson = data;
      console.log(data)
    })
  
  }

  goToVocabularyList() {
    this.router.navigate(['/admin/list-vocabulary']);
  }
}
