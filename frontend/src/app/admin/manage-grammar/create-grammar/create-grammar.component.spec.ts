import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGrammarComponent } from './create-grammar.component';

describe('CreateGrammarComponent', () => {
  let component: CreateGrammarComponent;
  let fixture: ComponentFixture<CreateGrammarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateGrammarComponent]
    });
    fixture = TestBed.createComponent(CreateGrammarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
