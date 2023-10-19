import { Component, OnInit } from '@angular/core';
import { Katakana } from 'src/app/core/models/katakana';
import { KatakanaService } from '../alphabet-services/katakana.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-katakana',
  templateUrl: './create-katakana.component.html',
  styleUrls: ['./create-katakana.component.css'],
})
export class CreateKatakanaComponent implements OnInit {
  katakana: Katakana = new Katakana();
  constructor(
    private katakanaService: KatakanaService,
    private router: Router
  ) {}
  ngOnInit(): void {}

  saveKatakana() {
    this.katakanaService.createKatakana(this.katakana).subscribe(
      (data) => {
        console.log(data);
        this.goToKatakanasList();
      },
      (error) => console.log(error)
    );
  }

  goToKatakanasList() {
    this.router.navigate(['admin/list-katakana']);
  }
  onSubmit() {
    console.log(this.katakana);
    this.saveKatakana();
  }
}
