import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailValidationEnseignantsComponent } from './detail-validation-enseignants.component';

describe('DetailValidationEnseignantsComponent', () => {
  let component: DetailValidationEnseignantsComponent;
  let fixture: ComponentFixture<DetailValidationEnseignantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailValidationEnseignantsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailValidationEnseignantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
