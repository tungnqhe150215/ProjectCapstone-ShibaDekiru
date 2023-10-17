import { TestBed } from '@angular/core/testing';

import { AdminManageListeningService } from './admin-manage-listening.service';

describe('AdminManageListeningService', () => {
  let service: AdminManageListeningService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminManageListeningService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
