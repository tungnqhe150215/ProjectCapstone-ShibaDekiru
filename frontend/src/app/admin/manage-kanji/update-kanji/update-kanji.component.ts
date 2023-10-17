import { Component } from '@angular/core';
import { Kanji } from 'src/app/core/models/kanji';
import { KanjiService } from '../kanji.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-kanji',
  templateUrl: './update-kanji.component.html',
  styleUrls: ['./update-kanji.component.css']
})
export class UpdateKanjiComponent {
  'id': number;
  kanji: Kanji = new Kanji();
  constructor(
    private kanjiService: KanjiService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.kanjiService.getKanjiByID(this.id).subscribe(
      (data) => {
        this.kanji = data;
      },
      (error) => console.log(error)
    );
  }
  onSubmit() {
    this.kanjiService.updateKanji(this.id, this.kanji).subscribe(
      (data) => {
        this.goToKanjiList();
      },
      (error) => console.log(error)
    );
  }

  goToKanjiList() {
    this.router.navigate(['/admin/list-kanji']);
  }
}
