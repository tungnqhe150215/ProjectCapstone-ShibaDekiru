import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrammarDetailComponent } from './grammar-detail.component';

describe('GrammarDetailComponent', () => {
  let component: GrammarDetailComponent;
  let fixture: ComponentFixture<GrammarDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GrammarDetailComponent]
    });
    fixture = TestBed.createComponent(GrammarDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
