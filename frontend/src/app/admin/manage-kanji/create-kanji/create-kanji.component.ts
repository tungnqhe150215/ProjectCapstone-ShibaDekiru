import { Component, OnInit } from '@angular/core';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-create-kanji',
  templateUrl: './create-kanji.component.html',
  styleUrls: ['./create-kanji.component.css']
})
export class CreateKanjiComponent implements OnInit {
  kanji: Kanji = new Kanji();
  constructor(private kanjiService: KanjiService, private router: Router,private nofiService: NotificationService) {}
  ngOnInit(): void {}

  saveKanji() {
    this.kanjiService.createKanji(this.kanji).subscribe(
      (data) => {
        console.log(data);
        this.goToKanjiesList();
        this.nofiService.openSnackBar('Tạo kanji thành công')
      },
      (error) => console.log(error)
    );
  }

  goToKanjiesList() {
    this.router.navigate(['admin/list-kanji']);
  }
  onSubmit() {
    console.log(this.kanji);
    this.saveKanji();
  }
}
