import { Component, OnInit } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-vocabulary',
  templateUrl: './create-vocabulary.component.html',
  styleUrls: ['./create-vocabulary.component.css'],
})
export class CreateVocabularyComponent implements OnInit {
  vocabulary: Vocabulary = new Vocabulary();
  constructor(
    private vocabularyService: VocabularyService,
    private router: Router
  ) {}
  ngOnInit(): void {}

  saveVocabulary() {
    this.vocabularyService.createVocabulary(this.vocabulary).subscribe(
      (data) => {
        console.log(data);
        this.goToVocabulariesList();
      },
      (error) => console.log(error)
    );
  }

  goToVocabulariesList() {
    this.router.navigate(['admin/list-vocabulary']);
  }
  onSubmit() {
    console.log(this.vocabulary);
    this.saveVocabulary();
  }
}
