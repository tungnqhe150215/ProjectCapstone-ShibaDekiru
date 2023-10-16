import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateKanjiComponent } from './create-kanji.component';

describe('CreateKanjiComponent', () => {
  let component: CreateKanjiComponent;
  let fixture: ComponentFixture<CreateKanjiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateKanjiComponent]
    });
    fixture = TestBed.createComponent(CreateKanjiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
