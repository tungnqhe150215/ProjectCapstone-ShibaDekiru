import { Component, OnInit } from '@angular/core';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-create-hiragana',
  templateUrl: './create-hiragana.component.html',
  styleUrls: ['./create-hiragana.component.css'],
})
export class CreateHiraganaComponent implements OnInit {
  hiragana: Hiragana = new Hiragana();
  constructor(private hiraganaService: HiraganaService, private router: Router, private nofiService: NotificationService ) {}
  ngOnInit(): void {}

  saveHiragana() {
    this.hiraganaService.createHiragana(this.hiragana).subscribe(
      (data) => {
        console.log(data);
        this.goToHiraganasList();
        this.nofiService.openSnackBar('Tạo hiragana thành công');
      },
      (error) => {
        console.log(error);
        this.nofiService.openSnackBar('Có lỗi xảy ra khi tạo hiragana')
      }
      
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
