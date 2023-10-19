import { Component } from '@angular/core';
import { Katakana } from 'src/app/core/models/katakana';
import { KatakanaService } from '../alphabet-services/katakana.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-katakana',
  templateUrl: './update-katakana.component.html',
  styleUrls: ['./update-katakana.component.css']
})
export class UpdateKatakanaComponent {
  'id': number;
  katakana: Katakana = new Katakana();
  constructor(
    private katakanaService: KatakanaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.katakanaService.getKatakanaByID(this.id).subscribe(
      (data) => {
        this.katakana = data;
      },
      (error) => console.log(error)
    );
  }
  onSubmit() {
    this.katakanaService.updateKatakana(this.id, this.katakana).subscribe(
      (data) => {
        this.goToKatakanasList();
      },
      (error) => console.log(error)
    );
  }

  goToKatakanasList() {
    this.router.navigate(['admin/list-katakana']);
  }
}