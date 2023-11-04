import { TestBed } from '@angular/core/testing';

import { LectureManageQuestionBankService } from './lecture-manage-question-bank.service';

describe('LectureManageQuestionBankService', () => {
  let service: LectureManageQuestionBankService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureManageQuestionBankService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
