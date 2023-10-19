import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateKatakanaComponent } from './create-katakana.component';

describe('CreateKatakanaComponent', () => {
  let component: CreateKatakanaComponent;
  let fixture: ComponentFixture<CreateKatakanaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateKatakanaComponent]
    });
    fixture = TestBed.createComponent(CreateKatakanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
