import { TestBed } from '@angular/core/testing';

import { AdminManageReadingService } from './admin-manage-reading.service';

describe('AdminManageReadingService', () => {
  let service: AdminManageReadingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageReadingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
