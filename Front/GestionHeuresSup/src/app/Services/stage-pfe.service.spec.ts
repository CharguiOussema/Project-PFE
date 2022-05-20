import { TestBed } from '@angular/core/testing';

import { StagePFEService } from './stage-pfe.service';

describe('StagePFEService', () => {
  let service: StagePFEService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StagePFEService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
