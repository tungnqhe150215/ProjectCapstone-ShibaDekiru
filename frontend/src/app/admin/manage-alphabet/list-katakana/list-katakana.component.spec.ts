import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListKatakanaComponent } from './list-katakana.component';

describe('ListKatakanaComponent', () => {
  let component: ListKatakanaComponent;
  let fixture: ComponentFixture<ListKatakanaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListKatakanaComponent]
    });
    fixture = TestBed.createComponent(ListKatakanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
