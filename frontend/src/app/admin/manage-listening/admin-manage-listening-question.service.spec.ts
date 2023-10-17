import { TestBed } from '@angular/core/testing';

import { AdminManageListeningQuestionService } from './admin-manage-listening-question.service';

describe('AdminManageListeningQuestionService', () => {
  let service: AdminManageListeningQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageListeningQuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
