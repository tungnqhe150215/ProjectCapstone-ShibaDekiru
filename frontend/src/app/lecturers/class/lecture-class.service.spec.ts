import { TestBed } from '@angular/core/testing';

import { LectureClassService } from './lecture-class.service';

describe('LectureClassService', () => {
  let service: LectureClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureClassService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
