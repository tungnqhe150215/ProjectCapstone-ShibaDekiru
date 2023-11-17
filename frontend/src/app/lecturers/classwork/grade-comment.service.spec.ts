import { TestBed } from '@angular/core/testing';

import { GradeCommentService } from './grade-comment.service';

describe('GradeCommentService', () => {
  let service: GradeCommentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GradeCommentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
