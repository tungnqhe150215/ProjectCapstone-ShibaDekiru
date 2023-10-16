import { TestBed } from '@angular/core/testing';

import { HiraganaService } from './hiragana.service';

describe('HiraganaService', () => {
  let service: HiraganaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HiraganaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
