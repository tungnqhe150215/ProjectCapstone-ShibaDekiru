import { Component, OnInit } from '@angular/core';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-hiragana',
  templateUrl: './create-hiragana.component.html',
  styleUrls: ['./create-hiragana.component.css'],
})
export class CreateHiraganaComponent implements OnInit {
  hiragana: Hiragana = new Hiragana();
  constructor(private hiraganaService: HiraganaService, private router: Router) {}
  ngOnInit(): void {}

  saveHiragana() {
    this.hiraganaService.createHiragana(this.hiragana).subscribe(
      (data) => {
        console.log(data);
        this.goToHiraganasList();
      },
      (error) => console.log(error)
    );
  }

  goToHiraganasList() {
    this.router.navigate(['admin/list-hiragana']);
  }
  onSubmit() {
    console.log(this.hiragana);
    this.saveHiragana();
  }
}
