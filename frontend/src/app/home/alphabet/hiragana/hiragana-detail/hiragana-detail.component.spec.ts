import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HiraganaDetailComponent } from './hiragana-detail.component';

describe('HiraganaDetailComponent', () => {
  let component: HiraganaDetailComponent;
  let fixture: ComponentFixture<HiraganaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HiraganaDetailComponent]
    });
    fixture = TestBed.createComponent(HiraganaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
