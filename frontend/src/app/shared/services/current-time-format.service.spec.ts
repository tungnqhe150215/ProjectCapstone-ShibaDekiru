import { TestBed } from '@angular/core/testing';

import { CurrentTimeFormatService } from './current-time-format.service';

describe('CurrentTimeFormatService', () => {
  let service: CurrentTimeFormatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrentTimeFormatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
