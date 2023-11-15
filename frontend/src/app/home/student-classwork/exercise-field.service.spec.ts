import { TestBed } from '@angular/core/testing';

import { ExerciseFieldService } from './exercise-field.service';

describe('ExerciseFieldService', () => {
  let service: ExerciseFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExerciseFieldService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
