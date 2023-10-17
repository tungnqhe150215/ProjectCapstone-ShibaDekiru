import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListVocabularyComponent } from './list-vocabulary.component';

describe('ListVocabularyComponent', () => {
  let component: ListVocabularyComponent;
  let fixture: ComponentFixture<ListVocabularyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListVocabularyComponent]
    });
    fixture = TestBed.createComponent(ListVocabularyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
