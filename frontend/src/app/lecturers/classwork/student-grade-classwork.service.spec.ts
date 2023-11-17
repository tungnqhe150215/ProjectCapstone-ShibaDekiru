import { TestBed } from '@angular/core/testing';

import { StudentGradeClassworkService } from './student-grade-classwork.service';

describe('StudentGradeClassworkService', () => {
  let service: StudentGradeClassworkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentGradeClassworkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
