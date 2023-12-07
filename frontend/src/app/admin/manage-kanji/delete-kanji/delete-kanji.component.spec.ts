import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteKanjiComponent } from './delete-kanji.component';

describe('DeleteKanjiComponent', () => {
  let component: DeleteKanjiComponent;
  let fixture: ComponentFixture<DeleteKanjiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteKanjiComponent]
    });
    fixture = TestBed.createComponent(DeleteKanjiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
