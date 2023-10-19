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
    this.getKatakana();
  }
  private getKatakana() {
    this.katakanaService.getKatakanaList().subscribe((data) => {
      this.katakanas = data;
    });
  }
  katakanaDetails(id: number) {
    this.router.navigate(['/admin/katakana-details', id]);
  }

  updateKatakana(id: number) {
    this.router.navigate(['/admin/update-katakana', id]);
  }
  deleteKatakana(id: number) {
    this.katakanaService.deleteKatakana(id).subscribe((data) => {
      console.log(data);
      this.getKatakana();
    });
  }
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}