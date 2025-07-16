import { TestBed } from '@angular/core/testing';
import { PatientService } from './patient';

describe('PatientService', () => {
  let service: PatientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PatientService] // ✅ Register the service
    });
    service = TestBed.inject(PatientService); // ✅ Inject the service
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
