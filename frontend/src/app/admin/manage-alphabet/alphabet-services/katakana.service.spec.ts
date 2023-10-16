import { TestBed } from '@angular/core/testing';

import { KatakanaService } from './katakana.service';

describe('KatakanaService', () => {
  let service: KatakanaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KatakanaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
