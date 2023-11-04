import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectureListTestComponent } from './lecture-list-test.component';

describe('LectureListTestComponent', () => {
  let component: LectureListTestComponent;
  let fixture: ComponentFixture<LectureListTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LectureListTestComponent]
    });
    fixture = TestBed.createComponent(LectureListTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
