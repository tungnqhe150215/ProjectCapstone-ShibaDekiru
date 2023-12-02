import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-list-hiragana',
  templateUrl: './list-hiragana.component.html',
  styleUrls: ['./list-hiragana.component.css'],
})
export class ListHiraganaComponent implements OnInit {
  'hiraganas': Hiragana[];
  filterText: string = '';
  p: number = 1;
  constructor(
    private hiraganaService: HiraganaService,
    private router: Router,
    private nofiService: NotificationService
  ) {}
  ngOnInit(): void {
    this.getHiragana();
  }
  private getHiragana() {
    this.hiraganaService.getHiraganaList().subscribe((data) => {
      this.hiraganas = data;
    });
  }

  hiraganaDetails(id: number) {
    this.router.navigate(['/admin/hiragana-details', id]);
  }
  updateHiragana(id: number) {
    this.router.navigate(['/admin/update-hiragana', id]);
  }
  deleteHiragana(id: number) {
    this.hiraganaService.deleteHiragana(id).subscribe((data) => {
      console.log(data);
      this.getHiragana();
      this.nofiService.openSnackBar('Xóa hiragana thành công');
    });
  }
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
