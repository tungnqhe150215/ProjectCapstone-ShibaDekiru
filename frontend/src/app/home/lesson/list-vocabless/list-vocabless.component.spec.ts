import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListVocablessComponent } from './list-vocabless.component';

describe('ListVocablessComponent', () => {
  let component: ListVocablessComponent;
  let fixture: ComponentFixture<ListVocablessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListVocablessComponent]
    });
    fixture = TestBed.createComponent(ListVocablessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
