import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListWritingQuestionComponent } from './list-writing-question.component';

describe('ListWritingQuestionComponent', () => {
  let component: ListWritingQuestionComponent;
  let fixture: ComponentFixture<ListWritingQuestionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListWritingQuestionComponent]
    });
    fixture = TestBed.createComponent(ListWritingQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
