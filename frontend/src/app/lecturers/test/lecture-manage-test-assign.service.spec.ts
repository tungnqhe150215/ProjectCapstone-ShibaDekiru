import { TestBed } from '@angular/core/testing';

import { LectureManageTestAssignService } from './lecture-manage-test-assign.service';

describe('LectureManageTestAssignService', () => {
  let service: LectureManageTestAssignService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureManageTestAssignService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
