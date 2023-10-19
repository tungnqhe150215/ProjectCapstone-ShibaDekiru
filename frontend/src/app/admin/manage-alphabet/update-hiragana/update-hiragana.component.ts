import { Component } from '@angular/core';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-hiragana',
  templateUrl: './update-hiragana.component.html',
  styleUrls: ['./update-hiragana.component.css'],
})
export class UpdateHiraganaComponent {
  'id': number;
  hiragana: Hiragana = new Hiragana();
  constructor(
    private hiraganaService: HiraganaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.hiraganaService.getHiraganaByID(this.id).subscribe(
      (data) => {
        this.hiragana = data;
      },
      (error) => console.log(error)
    );
  }
  onSubmit() {
    this.hiraganaService.updateHiragana(this.id, this.hiragana).subscribe(
      (data) => {
        this.goToHiraganasList();
      },
      (error) => console.log(error)
    );
  }

  goToHiraganasList() {
    this.router.navigate(['admin/list-hiragana']);
  }
}
