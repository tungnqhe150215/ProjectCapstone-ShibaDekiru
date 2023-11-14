import { TestBed } from '@angular/core/testing';

import { TestPermissionService } from './test-permission.service';

describe('TestPermissionService', () => {
  let service: TestPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TestPermissionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
