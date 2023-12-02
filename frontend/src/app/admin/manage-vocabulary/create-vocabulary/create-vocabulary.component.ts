import { Component, OnInit } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-create-vocabulary',
  templateUrl: './create-vocabulary.component.html',
  styleUrls: ['./create-vocabulary.component.css'],
})
export class CreateVocabularyComponent implements OnInit {
  vocabulary: Vocabulary = new Vocabulary();
  constructor(
    private vocabularyService: VocabularyService,
    private router: Router,
    private nofiService: NotificationService
  ) {}
  ngOnInit(): void {}

  saveVocabulary() {
    this.vocabularyService.createVocabulary(this.vocabulary).subscribe(
      (data) => {
        console.log(data);
        this.goToVocabulariesList();
        this.nofiService.openSnackBar('Tạo từ mới thành công')
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
