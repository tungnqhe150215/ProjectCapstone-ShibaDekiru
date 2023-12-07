import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteHiraganaComponent } from './delete-hiragana.component';

describe('DeleteHiraganaComponent', () => {
  let component: DeleteHiraganaComponent;
  let fixture: ComponentFixture<DeleteHiraganaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteHiraganaComponent]
    });
    fixture = TestBed.createComponent(DeleteHiraganaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
