import { TestBed } from '@angular/core/testing';

import { ManageBookService } from './manage-book.service';

describe('ManageBookService', () => {
  let service: ManageBookService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManageBookService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
