import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { Router } from '@angular/router';

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
    private router: Router
  ) {}
  ngOnInit(): void {
    this.getHiragana();
  }
  private getHiragana() {
    this.hiraganaService.getHiraganaList().subscribe((data) => {
      this.hiraganas = data;
    });
  }
}
