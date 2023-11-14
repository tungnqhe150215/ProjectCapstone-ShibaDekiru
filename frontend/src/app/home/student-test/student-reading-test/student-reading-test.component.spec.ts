import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentReadingTestComponent } from './student-reading-test.component';

describe('StudentReadingTestComponent', () => {
  let component: StudentReadingTestComponent;
  let fixture: ComponentFixture<StudentReadingTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentReadingTestComponent]
    });
    fixture = TestBed.createComponent(StudentReadingTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
