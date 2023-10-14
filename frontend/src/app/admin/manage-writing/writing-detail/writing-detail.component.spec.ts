import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingDetailComponent } from './writing-detail.component';

describe('WritingDetailComponent', () => {
  let component: WritingDetailComponent;
  let fixture: ComponentFixture<WritingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WritingDetailComponent]
    });
    fixture = TestBed.createComponent(WritingDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
