import { TestBed } from '@angular/core/testing';

import { StudentClassworkService } from './student-classwork.service';

describe('StudentClassworkService', () => {
  let service: StudentClassworkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentClassworkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
