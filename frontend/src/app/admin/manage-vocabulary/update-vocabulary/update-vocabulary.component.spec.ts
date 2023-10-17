import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateVocabularyComponent } from './update-vocabulary.component';

describe('UpdateVocabularyComponent', () => {
  let component: UpdateVocabularyComponent;
  let fixture: ComponentFixture<UpdateVocabularyComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateVocabularyComponent]
    });
    fixture = TestBed.createComponent(UpdateVocabularyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
