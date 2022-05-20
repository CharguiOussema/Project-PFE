import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateChargeHoraireS2Component } from './update-charge-horaire-s2.component';

describe('UpdateChargeHoraireS2Component', () => {
  let component: UpdateChargeHoraireS2Component;
  let fixture: ComponentFixture<UpdateChargeHoraireS2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateChargeHoraireS2Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateChargeHoraireS2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
