import { TestBed } from '@angular/core/testing';

import { AdminManageClassService } from './admin-manage-class.service';

describe('AdminManageClassService', () => {
  let service: AdminManageClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageClassService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
