import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateHiraganaComponent } from './create-hiragana.component';

describe('CreateHiraganaComponent', () => {
  let component: CreateHiraganaComponent;
  let fixture: ComponentFixture<CreateHiraganaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateHiraganaComponent]
    });
    fixture = TestBed.createComponent(CreateHiraganaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
