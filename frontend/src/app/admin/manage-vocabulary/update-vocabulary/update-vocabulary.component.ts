import { Component } from '@angular/core';
import { Vocabulary } from 'src/app/core/models/vocabulary';
import { VocabularyService } from '../vocabulary.service';
import { ActivatedRoute, Router } from '@angular/router';

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
    private router: Router
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
      },
      (error) => console.log(error)
    );
  }

  goToVocabularyList() {
    this.router.navigate(['/admin/list-vocabulary']);
  }
}
