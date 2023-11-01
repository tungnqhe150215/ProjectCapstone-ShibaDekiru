import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectureTestDetailComponent } from './lecture-test-detail.component';

describe('LectureTestDetailComponent', () => {
  let component: LectureTestDetailComponent;
  let fixture: ComponentFixture<LectureTestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LectureTestDetailComponent]
    });
    fixture = TestBed.createComponent(LectureTestDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
