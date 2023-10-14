import { TestBed } from '@angular/core/testing';

import { AdminManageWritingQuestionService } from './admin-manage-writing-question.service';

describe('AdminManageWritingQuestionService', () => {
  let service: AdminManageWritingQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageWritingQuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
