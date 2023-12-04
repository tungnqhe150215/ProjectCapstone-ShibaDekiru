import { Component, OnInit } from '@angular/core';
import { Katakana } from 'src/app/core/models/katakana';
import { KatakanaService } from '../alphabet-services/katakana.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-create-katakana',
  templateUrl: './create-katakana.component.html',
  styleUrls: ['./create-katakana.component.css'],
})
export class CreateKatakanaComponent implements OnInit {
  katakana: Katakana = new Katakana();
  constructor(
    private katakanaService: KatakanaService,
    private router: Router,
    private nofiService: NotificationService
  ) {}
  ngOnInit(): void {}

  saveKatakana() {
    this.katakanaService.createKatakana(this.katakana).subscribe(
      (data) => {
        console.log(data);
        this.goToKatakanasList();
        this.nofiService.openSnackBar('Tạo katakana thành công');
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
