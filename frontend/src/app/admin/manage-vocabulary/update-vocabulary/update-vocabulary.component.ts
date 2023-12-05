import { Component } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-update-vocabulary',
  templateUrl: './update-vocabulary.component.html',
  styleUrls: ['./update-vocabulary.component.css']
})
export class UpdateVocabularyComponent {
  'id': number;
  vocabulary: Vocabulary = new Vocabulary();
  constructor(
    private vocabularyService: VocabularyService,
    private route: ActivatedRoute,
    private router: Router,
    private nofiService: NotificationService
  ) {}
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.vocabularyService.getVocabularyByID(this.id).subscribe(
      (data) => {
        this.vocabulary = data;
      },
      (error) => console.log(error)
    );
  }
  onSubmit() {
    this.vocabularyService.updateVocabulary(this.id, this.vocabulary).subscribe(
      (data) => {
        this.goToVocabularyList();
        this.nofiService.openSnackBar('Cập nhật từ mới thành công');
      },
      (error) => console.log(error)
    );
  }

  goToVocabularyList() {
    this.router.navigate(['/admin/list-vocabulary']);
  }
}
