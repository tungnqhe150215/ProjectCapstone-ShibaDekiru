import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteGrammarComponent } from './delete-grammar.component';

describe('DeleteGrammarComponent', () => {
  let component: DeleteGrammarComponent;
  let fixture: ComponentFixture<DeleteGrammarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteGrammarComponent]
    });
    fixture = TestBed.createComponent(DeleteGrammarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
