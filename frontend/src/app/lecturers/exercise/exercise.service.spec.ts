import { TestBed } from '@angular/core/testing';

import { LectureManageExerciseService } from './lecture-manage-exercise.service';

describe('ExerciseService', () => {
  let service: LectureManageExerciseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LectureManageExerciseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
