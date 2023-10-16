import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListHiraganaComponent } from './list-hiragana.component';

describe('ListHiraganaComponent', () => {
  let component: ListHiraganaComponent;
  let fixture: ComponentFixture<ListHiraganaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListHiraganaComponent]
    });
    fixture = TestBed.createComponent(ListHiraganaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
