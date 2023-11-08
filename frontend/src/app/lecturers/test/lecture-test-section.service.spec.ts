import { TestBed } from '@angular/core/testing';

import { LectureTestSectionService } from './lecture-test-section.service';

describe('LectureTestSectionService', () => {
  let service: LectureTestSectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureTestSectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
