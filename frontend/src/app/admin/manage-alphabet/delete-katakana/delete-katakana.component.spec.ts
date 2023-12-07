import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteKatakanaComponent } from './delete-katakana.component';

describe('DeleteKatakanaComponent', () => {
  let component: DeleteKatakanaComponent;
  let fixture: ComponentFixture<DeleteKatakanaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteKatakanaComponent]
    });
    fixture = TestBed.createComponent(DeleteKatakanaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
