import { Component } from '@angular/core';
import { Grammar } from 'src/app/core/models/grammar';
import { GrammarService } from '../grammar.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-update-grammar',
  templateUrl: './update-grammar.component.html',
  styleUrls: ['./update-grammar.component.css']
})
export class UpdateGrammarComponent {
  'id': number;
  grammar: Grammar = new Grammar();
  constructor(
    private grammarService: GrammarService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService
  ) {}
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.grammarService.getGrammarByID(this.id).subscribe(
      (data) => {
        this.grammar = data;
      },
      (error) => console.log(error)
    );
  }
  onSubmit() {
    this.grammarService.updateGrammar(this.id, this.grammar).subscribe(
      (data) => {
        this.goToGrammarsList();
        this.nofiService.openSnackBar('Cập nhật ngữ pháp thành công');
      },
      (error) => console.log(error)
    );
  }

  goToGrammarsList() {
    this.router.navigate(['/admin/list-grammar']);
  }
}

