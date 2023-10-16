import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';

@Component({
  selector: 'app-list-kanji',
  templateUrl: './list-kanji.component.html',
  styleUrls: ['./list-kanji.component.css'],
})
export class ListKanjiComponent implements OnInit {
  'kanjis': Kanji[];
  filterText: string = '';
  p: number = 1;
  constructor(private kanjiService: KanjiService, private router: Router) {}
  ngOnInit(): void {
    this.getKanjis();
  }
  private getKanjis() {
    this.kanjiService.getKanjisList().subscribe((data) => {
      this.kanjis = data;
    });
  }
  // courseDetails(id: number) {
  //   this.router.navigate(['course-details', id]);
  // }
  // updateCourse(id: number) {
  //   this.router.navigate(['update-course', id]);
  // }
  // deleteCourse(id: number) {
  //   this.kanjiService.deleteKanji(id).subscribe((data) => {
  //     console.log(data);
  //     this.getKanjis();
  //   });
  // }
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
