import { Component, OnInit } from '@angular/core';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-kanji',
  templateUrl: './create-kanji.component.html',
  styleUrls: ['./create-kanji.component.css']
})
export class CreateKanjiComponent implements OnInit {
  kanji: Kanji = new Kanji();
  constructor(private kanjiService: KanjiService, private router: Router) {}
  ngOnInit(): void {}

  saveCourse() {
    this.kanjiService.createKanji(this.kanji).subscribe(
      (data) => {
        console.log(data);
        this.goToCourseList();
      },
      (error) => console.log(error)
    );
  }

  goToCourseList() {
    this.router.navigate(['admin/list-kanji']);
  }
  onSubmit() {
    console.log(this.kanji);
    this.saveCourse();
  }
}