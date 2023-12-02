import { Component, OnInit } from '@angular/core';
import { Grammar } from 'src/app/core/models/grammar';
import { GrammarService } from '../grammar.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-create-grammar',
  templateUrl: './create-grammar.component.html',
  styleUrls: ['./create-grammar.component.css']
})
export class CreateGrammarComponent implements OnInit {
  grammar: Grammar = new Grammar();
  constructor(private grammarService: GrammarService, private router: Router,private nofiService: NotificationService) {}
  ngOnInit(): void {}

  saveGrammar() {
    this.grammarService.createGrammar(this.grammar).subscribe(
      (data) => {
        console.log(data);
        this.goToGrammarsList();
        this.nofiService.openSnackBar('Tạo ngữ pháp thành công!');
      },
      (error) => console.log(error)
    );
  }

  goToGrammarsList() {
    this.router.navigate(['admin/list-grammar']);
  }
  onSubmit() {
    console.log(this.grammar);
    this.saveGrammar();
  }
}
