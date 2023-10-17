import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateGrammarComponent } from './update-grammar.component';

describe('UpdateGrammarComponent', () => {
  let component: UpdateGrammarComponent;
  let fixture: ComponentFixture<UpdateGrammarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateGrammarComponent]
    });
    fixture = TestBed.createComponent(UpdateGrammarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
