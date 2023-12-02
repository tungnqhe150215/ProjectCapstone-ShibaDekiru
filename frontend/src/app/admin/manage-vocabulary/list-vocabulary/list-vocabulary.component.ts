import { Component, OnInit } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-list-vocabulary',
  templateUrl: './list-vocabulary.component.html',
  styleUrls: ['./list-vocabulary.component.css'],
})
export class ListVocabularyComponent implements OnInit {
  'vocabularies': Vocabulary[];
  filterText: string = '';
  p: number = 1;
  constructor(
    private vocabularyService: VocabularyService,
    private router: Router,
    private nofiService: NotificationService
  ) {}
  ngOnInit(): void {
    this.getVocabularies();
  }
  private getVocabularies() {
    this.vocabularyService.getVocabulariesList().subscribe((data) => {
      this.vocabularies = data;
    });
  }
  vocabularyDetails(id: number) {
    this.router.navigate(['/admin/vocabulary-details', id]);
  }
  updateVocabulary(id: number) {
    this.router.navigate(['/admin/update-vocabulary', id]);
  }
  deleteVocabulary(id: number) {
    this.vocabularyService.deleteVocabulary(id).subscribe((data) => {
      console.log(data);
      this.getVocabularies();
      this.nofiService.openSnackBar('Xóa từ mới thành công');
    });
  }
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
