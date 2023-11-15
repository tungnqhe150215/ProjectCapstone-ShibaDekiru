import { TestBed } from '@angular/core/testing';

import { ClassworkAnswerService } from './classwork-answer.service';

describe('ClassworkAnswerService', () => {
  let service: ClassworkAnswerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClassworkAnswerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
