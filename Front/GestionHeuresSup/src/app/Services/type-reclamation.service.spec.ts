import { TestBed } from '@angular/core/testing';

import { TypeReclamationService } from './type-reclamation.service';

describe('TypeReclamationService', () => {
  let service: TypeReclamationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypeReclamationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
