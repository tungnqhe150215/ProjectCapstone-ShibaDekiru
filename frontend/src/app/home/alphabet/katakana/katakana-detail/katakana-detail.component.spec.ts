import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KatakanaDetailComponent } from './katakana-detail.component';

describe('KatakanaDetailComponent', () => {
  let component: KatakanaDetailComponent;
  let fixture: ComponentFixture<KatakanaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KatakanaDetailComponent]
    });
    fixture = TestBed.createComponent(KatakanaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
