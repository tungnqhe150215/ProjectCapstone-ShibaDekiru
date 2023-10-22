import { TestBed } from '@angular/core/testing';

import { LecPostService } from './lec-post.service';

describe('LecPostService', () => {
  let service: LecPostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LecPostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
