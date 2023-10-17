import { Component, OnInit } from '@angular/core';
import { Grammar } from 'src/app/core/models/grammar';
import { GrammarService } from '../grammar.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-grammar',
  templateUrl: './create-grammar.component.html',
  styleUrls: ['./create-grammar.component.css']
})
export class CreateGrammarComponent implements OnInit {
  grammar: Grammar = new Grammar();
  constructor(private grammarService: GrammarService, private router: Router) {}
  ngOnInit(): void {}

  saveGrammar() {
    this.grammarService.createGrammar(this.grammar).subscribe(
      (data) => {
        console.log(data);
        this.goToGrammarsList();
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
