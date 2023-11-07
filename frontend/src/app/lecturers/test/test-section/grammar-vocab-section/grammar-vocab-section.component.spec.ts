import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrammarVocabSectionComponent } from './grammar-vocab-section.component';

describe('GrammarVocabSectionComponent', () => {
  let component: GrammarVocabSectionComponent;
  let fixture: ComponentFixture<GrammarVocabSectionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GrammarVocabSectionComponent]
    });
    fixture = TestBed.createComponent(GrammarVocabSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
