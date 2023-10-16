import { Component, OnInit } from '@angular/core';
import { Katakana } from 'src/app/core/models/katakana';
import { KatakanaService } from '../alphabet-services/katakana.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-katakana',
  templateUrl: './list-katakana.component.html',
  styleUrls: ['./list-katakana.component.css']
})
export class ListKatakanaComponent implements OnInit {
  'katakanas': Katakana[];
  filterText: string = '';
  p: number = 1;
  constructor(
    private katakanaService: KatakanaService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.getHiragana();
  }
  private getHiragana() {
    this.katakanaService.getKatakanaList().subscribe((data) => {
      this.katakanas = data;
    });
  }
}