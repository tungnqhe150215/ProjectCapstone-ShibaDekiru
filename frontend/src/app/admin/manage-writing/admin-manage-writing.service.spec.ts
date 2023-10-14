import { TestBed } from '@angular/core/testing';

import { AdminManageWritingService } from './admin-manage-writing.service';

describe('AdminManageWritingService', () => {
  let service: AdminManageWritingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageWritingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
