import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-list-kanji',
  templateUrl: './list-kanji.component.html',
  styleUrls: ['./list-kanji.component.css'],
})
export class ListKanjiComponent implements OnInit {
  'kanjis': Kanji[];
  filterText: string = '';
  p: number = 1;
  constructor(private kanjiService: KanjiService, private router: Router, private nofiService: NotificationService) {}
  ngOnInit(): void {
    this.getKanjis();
  }
  private getKanjis() {
    this.kanjiService.getKanjisList().subscribe((data) => {
      this.kanjis = data;
    });
  }
  kanjiDetails(id: number) {
    this.router.navigate(['/admin/kanji-details', id]);
  }
  updateKanji(kanjiId: number) {
    this.router.navigate(['/admin/update-kanji', kanjiId]);
  }
  deleteKanji(id: number) {
    this.kanjiService.deleteKanji(id).subscribe((data) => {
      console.log(data);
      this.getKanjis();
      this.nofiService.openSnackBar('Xóa kanji thành công');
    });
  }
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
