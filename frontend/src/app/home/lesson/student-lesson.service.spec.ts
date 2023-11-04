import { TestBed } from '@angular/core/testing';

import { StudentLessonService } from './student-lesson.service';

describe('StudentLessonService', () => {
  let service: StudentLessonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentLessonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
