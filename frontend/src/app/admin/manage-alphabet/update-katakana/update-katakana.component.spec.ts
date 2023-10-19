import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateKatakanaComponent } from './update-katakana.component';

describe('UpdateKatakanaComponent', () => {
  let component: UpdateKatakanaComponent;
  let fixture: ComponentFixture<UpdateKatakanaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateKatakanaComponent]
    });
    fixture = TestBed.createComponent(UpdateKatakanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
