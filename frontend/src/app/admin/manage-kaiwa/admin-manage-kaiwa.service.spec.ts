import { TestBed } from '@angular/core/testing';

import { AdminManageKaiwaService } from './admin-manage-kaiwa.service';

describe('AdminManageKaiwaService', () => {
  let service: AdminManageKaiwaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageKaiwaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
