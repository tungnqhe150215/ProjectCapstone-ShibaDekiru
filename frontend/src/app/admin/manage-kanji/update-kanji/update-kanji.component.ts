import { Component } from '@angular/core';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { Lesson } from 'src/app/core/models/lesson';
import { LessonService } from 'src/app/core/services/lesson.service';

@Component({
  selector: 'app-update-kanji',
  templateUrl: './update-kanji.component.html',
  styleUrls: ['./update-kanji.component.css']
})
export class UpdateKanjiComponent {
  'id': number;
  kanji: Kanji = new Kanji();
  lesson: Lesson[] =[];
  constructor(
    private kanjiService: KanjiService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService,
    private lessonService: LessonService,
  ) {}
  ngOnInit(): void {
    this.getLesson();
    this.id = this.route.snapshot.params['id'];

    this.kanjiService.getKanjiByID(this.id).subscribe(
      (data) => {
        this.kanji = data;
      },
      (error) => {
        this.nofiService.openSnackBar("Đã có lỗi xảy ra khi lấy thông tin chữ Hán");
        console.log(error);
      }
    );
  }
  onSubmit() {
    this.kanjiService.updateKanji(this.id, this.kanji).subscribe(
      (data) => {
        this.goToKanjiList();
        this.nofiService.openSnackBar('Cập nhật kanji thành công');
      },
      (error) => {
        console.log(error);
        this.nofiService.openSnackBar('Đã xảy ra lỗi! Vui lòng thử lại');
      }
    );
  }

  private getLesson(){
    this.lessonService.getLessonNoneID().subscribe( data =>{
      this.lesson = data;
      console.log(data)
    })
  
  }

  goToKanjiList() {
    this.router.navigate(['/admin/list-kanji']);
  }
}
