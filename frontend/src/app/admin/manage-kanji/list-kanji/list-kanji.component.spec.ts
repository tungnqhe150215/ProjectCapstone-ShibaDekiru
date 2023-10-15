import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListKanjiComponent } from './list-kanji.component';

describe('ListKanjiComponent', () => {
  let component: ListKanjiComponent;
  let fixture: ComponentFixture<ListKanjiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListKanjiComponent]
    });
    fixture = TestBed.createComponent(ListKanjiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
