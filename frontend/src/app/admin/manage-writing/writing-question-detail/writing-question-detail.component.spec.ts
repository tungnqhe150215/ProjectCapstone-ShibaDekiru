import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingQuestionDetailComponent } from './writing-question-detail.component';

describe('WritingQuestionDetailComponent', () => {
  let component: WritingQuestionDetailComponent;
  let fixture: ComponentFixture<WritingQuestionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WritingQuestionDetailComponent]
    });
    fixture = TestBed.createComponent(WritingQuestionDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
