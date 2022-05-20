import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateChargePFEComponent } from './update-charge-pfe.component';

describe('UpdateChargePFEComponent', () => {
  let component: UpdateChargePFEComponent;
  let fixture: ComponentFixture<UpdateChargePFEComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateChargePFEComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateChargePFEComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
