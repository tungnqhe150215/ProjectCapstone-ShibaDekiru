import { TestBed } from '@angular/core/testing';

import { LecturerManageWritingExerciseService } from './lecturer-manage-writing-exercise.service';

describe('LecturerManageWritingExerciseService', () => {
  let service: LecturerManageWritingExerciseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LecturerManageWritingExerciseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
