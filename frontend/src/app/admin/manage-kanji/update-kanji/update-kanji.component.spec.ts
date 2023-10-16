import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateKanjiComponent } from './update-kanji.component';

describe('UpdateKanjiComponent', () => {
  let component: UpdateKanjiComponent;
  let fixture: ComponentFixture<UpdateKanjiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateKanjiComponent]
    });
    fixture = TestBed.createComponent(UpdateKanjiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
