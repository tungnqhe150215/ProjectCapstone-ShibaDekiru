import { TestBed } from '@angular/core/testing';

import { AdminManageReadingQuestionService } from './admin-manage-reading-question.service';

describe('AdminManageReadingQuestionService', () => {
  let service: AdminManageReadingQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageReadingQuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
