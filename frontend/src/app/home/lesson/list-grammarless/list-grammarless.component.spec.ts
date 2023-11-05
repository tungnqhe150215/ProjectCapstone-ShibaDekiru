import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListGrammarlessComponent } from './list-grammarless.component';

describe('ListGrammarlessComponent', () => {
  let component: ListGrammarlessComponent;
  let fixture: ComponentFixture<ListGrammarlessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListGrammarlessComponent]
    });
    fixture = TestBed.createComponent(ListGrammarlessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
