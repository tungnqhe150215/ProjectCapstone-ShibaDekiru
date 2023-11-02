import { TestBed } from '@angular/core/testing';

import { KnowledgeServiceService } from './knowledge-service.service';

describe('KnowledgeServiceService', () => {
  let service: KnowledgeServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KnowledgeServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
