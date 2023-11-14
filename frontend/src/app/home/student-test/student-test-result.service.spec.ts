import { TestBed } from '@angular/core/testing';

import { StudentTestResultService } from './student-test-result.service';

describe('StudentTestResultService', () => {
  let service: StudentTestResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentTestResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
