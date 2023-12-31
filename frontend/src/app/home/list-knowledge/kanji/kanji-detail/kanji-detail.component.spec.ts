import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanjiDetailComponent } from './kanji-detail.component';

describe('KanjiDetailComponent', () => {
  let component: KanjiDetailComponent;
  let fixture: ComponentFixture<KanjiDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KanjiDetailComponent]
    });
    fixture = TestBed.createComponent(KanjiDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
