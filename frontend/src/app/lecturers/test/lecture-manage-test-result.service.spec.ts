import { TestBed } from '@angular/core/testing';

import { LectureManageTestResultService } from './lecture-manage-test-result.service';

describe('LectureManageTestResultService', () => {
  let service: LectureManageTestResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureManageTestResultService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
