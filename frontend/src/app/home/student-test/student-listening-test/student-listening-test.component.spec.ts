import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentListeningTestComponent } from './student-listening-test.component';

describe('StudentListeningTestComponent', () => {
  let component: StudentListeningTestComponent;
  let fixture: ComponentFixture<StudentListeningTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentListeningTestComponent]
    });
    fixture = TestBed.createComponent(StudentListeningTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
