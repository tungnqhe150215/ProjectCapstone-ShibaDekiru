import { TestBed } from '@angular/core/testing';

import { LectureManageTestService } from './lecture-manage-test.service';

describe('LectureManageTestService', () => {
  let service: LectureManageTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureManageTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
