import { Component, OnInit } from '@angular/core';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LessonService } from 'src/app/core/services/lesson.service';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-create-kanji',
  templateUrl: './create-kanji.component.html',
  styleUrls: ['./create-kanji.component.css']
})
export class CreateKanjiComponent implements OnInit {
  kanji: Kanji = new Kanji();
  lesson: Lesson[] =[];
  constructor(private kanjiService: KanjiService, private router: Router,private nofiService: NotificationService, private lessonService: LessonService, ) {}
  ngOnInit(): void {
    this.getLesson();
  }

  saveKanji() {
    this.kanjiService.createKanji(this.kanji).subscribe({
      next:(res) =>{
        console.log(res);
        this.goToKanjiesList();
        this.nofiService.openSnackBar('Tạo chữ Hán thành công')
      },
      error: (err) => {
        console.error(err);
        this.nofiService.openSnackBar('Đã xảy ra lỗi khi tạo chữ Hán! Vui lòng thử lại');
      },
    });
  }

  private getLesson(){
    this.lessonService.getLessonNoneID().subscribe( data =>{
      this.lesson = data;
      console.log(data)
    })
  
  }


  goToKanjiesList() {
    this.router.navigate(['admin/list-kanji']);
  }
  onSubmit() {
    console.log(this.kanji);
    this.saveKanji();
  }
}
