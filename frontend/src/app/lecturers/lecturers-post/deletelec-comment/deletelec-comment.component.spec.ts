import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletelecCommentComponent } from './deletelec-comment.component';

describe('DeletelecCommentComponent', () => {
  let component: DeletelecCommentComponent;
  let fixture: ComponentFixture<DeletelecCommentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeletelecCommentComponent]
    });
    fixture = TestBed.createComponent(DeletelecCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
