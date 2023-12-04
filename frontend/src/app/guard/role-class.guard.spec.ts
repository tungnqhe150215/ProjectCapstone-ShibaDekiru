import { TestBed } from '@angular/core/testing';

import { RoleClassGuard } from './role-class.guard';

describe('RoleClassGuard', () => {
  let guard: RoleClassGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RoleClassGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
