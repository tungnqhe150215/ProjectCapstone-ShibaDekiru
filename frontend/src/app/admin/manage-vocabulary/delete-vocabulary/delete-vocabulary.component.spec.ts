import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteVocabularyComponent } from './delete-vocabulary.component';

describe('DeleteVocabularyComponent', () => {
  let component: DeleteVocabularyComponent;
  let fixture: ComponentFixture<DeleteVocabularyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteVocabularyComponent]
    });
    fixture = TestBed.createComponent(DeleteVocabularyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
