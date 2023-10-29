import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KatakanaComponent } from './katakana.component';

describe('KatakanaComponent', () => {
  let component: KatakanaComponent;
  let fixture: ComponentFixture<KatakanaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KatakanaComponent]
    });
    fixture = TestBed.createComponent(KatakanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
