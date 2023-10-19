import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateHiraganaComponent } from './update-hiragana.component';

describe('UpdateHiraganaComponent', () => {
  let component: UpdateHiraganaComponent;
  let fixture: ComponentFixture<UpdateHiraganaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateHiraganaComponent]
    });
    fixture = TestBed.createComponent(UpdateHiraganaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
