import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateChargeHoraireS1Component } from './update-charge-horaire-s1.component';

describe('UpdateChargeHoraireS1Component', () => {
  let component: UpdateChargeHoraireS1Component;
  let fixture: ComponentFixture<UpdateChargeHoraireS1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateChargeHoraireS1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateChargeHoraireS1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
