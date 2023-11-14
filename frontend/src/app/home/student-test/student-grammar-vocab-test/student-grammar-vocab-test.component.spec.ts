import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentGrammarVocabTestComponent } from './student-grammar-vocab-test.component';

describe('StudentGrammarVocabTestComponent', () => {
  let component: StudentGrammarVocabTestComponent;
  let fixture: ComponentFixture<StudentGrammarVocabTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentGrammarVocabTestComponent]
    });
    fixture = TestBed.createComponent(StudentGrammarVocabTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
