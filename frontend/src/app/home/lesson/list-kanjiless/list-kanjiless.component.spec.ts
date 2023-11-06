import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListKanjilessComponent } from './list-kanjiless.component';

describe('ListKanjilessComponent', () => {
  let component: ListKanjilessComponent;
  let fixture: ComponentFixture<ListKanjilessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListKanjilessComponent]
    });
    fixture = TestBed.createComponent(ListKanjilessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
