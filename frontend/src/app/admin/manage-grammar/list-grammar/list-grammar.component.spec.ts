import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListGrammarComponent } from './list-grammar.component';

describe('ListGrammarComponent', () => {
  let component: ListGrammarComponent;
  let fixture: ComponentFixture<ListGrammarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListGrammarComponent]
    });
    fixture = TestBed.createComponent(ListGrammarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
