import { Component } from '@angular/core';
import { Hiragana } from 'src/app/core/models/hiragana';
import { HiraganaService } from '../alphabet-services/hiragana.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

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
    private router: Router,
    private nofiService: NotificationService
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
        this.nofiService.openSnackBar('Cập nhật hiragana thành công');
      },
      (error) => console.log(error)
    );
  }

  goToHiraganasList() {
    this.router.navigate(['admin/list-hiragana']);
  }
}
